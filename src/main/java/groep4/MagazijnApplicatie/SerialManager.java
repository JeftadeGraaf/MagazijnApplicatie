package groep4.MagazijnApplicatie;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.awt.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SerialManager implements SerialPortDataListener {
    private final SerialPort comPort;
    private final StringBuilder messageBuffer = new StringBuilder();
    private GUI gui;

    public SerialManager(GUI gui, SerialPort comPort) {
        this.gui = gui;
        this.comPort = comPort;
        this.comPort.setBaudRate(115200);
        this.comPort.openPort();
        this.comPort.addDataListener(this);
    }

    public SerialManager(GUI gui){
        this.gui = gui;
        this.comPort = SerialPort.getCommPorts()[0];
        this.comPort.setBaudRate(115200);
        this.comPort.openPort();
        this.comPort.addDataListener(this);
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
                gui.changeStatus("automatisch", Color.green);
                break;
            case 'r':
                gui.changeStatus("noodstop", Color.red);
                break;
            case 'o':
                gui.changeStatus("handmatig", Color.orange);
                break;
            case 'b':
                gui.changeStatus("kalibratie", Color.blue);
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
            e.printStackTrace();
            System.out.println("Invalid location coordinates: " + message);
        }
    }

    public void sendMessage(String message) {
        System.out.println("Sending message: " + message);
        comPort.writeBytes(message.getBytes(), message.length());
    }
}
