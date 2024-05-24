package groep4.MagazijnApplicatie;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.awt.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SerialManager implements SerialPortDataListener {
    private final SerialPort comPort;
    private final StringBuilder messageBuffer = new StringBuilder();
    private final GUI gui;

    public SerialManager(SerialPort comPort, GUI gui) {
        this.comPort = comPort;
        this.gui = gui;
    }

    public SerialManager(GUI gui){
        this.gui = gui;
        this.comPort = SerialPort.getCommPorts()[0];
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
            String[] messages = messageBuffer.toString().split("\n");
            if (messages.length > 1) {
                // Process each complete message
                for (int i = 0; i < messages.length - 1; i++) {
                    String message = messages[i];
                    System.out.println(message);
                    System.out.println(message);
                    char testChar = messages[i].charAt(0);
                    switch (testChar) {
                        case 's':
                            sendMessage("bs");
                            switch (messages[i].charAt(1)){
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
                                    gui.changeStatus("calibratie", Color.blue);
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case 'l':
                            message = message.substring(1);
                            String[] location = message.split(",");
                            gui.setRobotXCoordinate(Integer.parseInt(location[0]));
                            gui.setRobotYCoordinate(Integer.parseInt(location[1]));
                        default:
                            break;
                    }
                }
                // Reset buffer with any remaining incomplete message
                messageBuffer.setLength(0);
                messageBuffer.append(messages[messages.length - 1]);
            }
        }
    }

    public void sendMessage(String message) {
        comPort.writeBytes(message.getBytes(), message.length());
    }
}
