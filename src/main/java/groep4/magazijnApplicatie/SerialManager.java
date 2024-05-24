package groep4.magazijnApplicatie;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SerialManager implements SerialPortDataListener {
    private SerialPort comPort = null;
    private final StringBuilder messageBuffer = new StringBuilder();
    private final GUI gui;

    public SerialManager(GUI gui){
        this.gui = gui;
        try {
            this.comPort = SerialPort.getCommPorts()[0];
            this.comPort.setBaudRate(115200);
            this.comPort.openPort();
            this.comPort.addDataListener(this);
        } catch (ArrayIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "Geen verbinding met robot mogelijk.", "Fout", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
            return;

        int bytesAvailable = comPort.bytesAvailable();
        if (bytesAvailable > 0) {
            byte[] newData = new byte[bytesAvailable];
            int numRead = comPort.readBytes(newData, bytesAvailable);

            // Append received bytes to message buffer
            messageBuffer.append(new String(newData, 0, numRead, UTF_8));

            // Check for complete messages (ending with '\n')
            String bufferString = messageBuffer.toString();
            int lastIndex = bufferString.lastIndexOf('\n');
            if (lastIndex != -1) {
                String completeMessages = bufferString.substring(0, lastIndex);
                messageBuffer.delete(0, lastIndex + 1);

                // Process each complete message
                String[] messages = completeMessages.split("\n");
                for (String message : messages) {
                    System.out.println(message);
                    char testChar = message.charAt(0);
                    switch (testChar) {
                        case 's':
                            handleStatusMessage(message);
                            break;
                        case 'l':
                            handleLocationMessage(message);
                            break;
                        case 'p':
                            handleRetrievedPackageMessage(message);
                            break;
                        default:
                            System.out.println("Unknown message type: " + testChar);
                            break;
                    }
                }
            }
        }
    }

    private void handleStatusMessage(String message) {
        switch (message.charAt(1)) {
            case 'g':
                gui.changeStatus("automatisch");
                break;
            case 'r':
                gui.changeStatus("noodstop");
                break;
            case 'o':
                gui.changeStatus("handmatig");
                break;
            case 'b':
                gui.changeStatus("kalibratie");
                break;
            default:
                System.out.println("Unknown status type: " + message.charAt(1));
                break;
        }
    }

    private void handleLocationMessage(String message) {
        try {
            message = message.substring(1);
            String[] location = message.split(",");
            if (location.length == 2) {
//                System.err.println(location[1]);
                int x = Integer.parseInt(location[0]);
                int y = Integer.parseInt(location[1].trim());
                gui.getRealtimeLocation().setCoordinates(x, y);
//                System.out.println("Location updated: X=" + x + ", Y=" + y);
            } else {
                System.out.println("Invalid location message format: " + message);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid location coordinates: " + message);
        }
    }

    private void handleRetrievedPackageMessage(String message){
        try {
            message = message.substring(1);
            String[] location = message.split(",");
            for (String s : location) {
                System.out.println(s);
            }
            if (location.length == 2) {
                int x = Integer.parseInt(location[0]);
                int y = Integer.parseInt(location[1].trim());
                gui.getRealtimeLocation().addRetrievedProduct(x,y);
                gui.getRealtimeLocation().repaint();
            } else {
                System.out.println("Invalid retrieved package message format: " + message);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid retrieved package coordinates: " + message);
        }
    }

    public void sendMessage(String message) {
        System.out.println("Sending message: " + message);
        comPort.writeBytes(message.getBytes(), message.length());
    }
}
