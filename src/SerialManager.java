import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.UnsupportedEncodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SerialManager implements SerialPortDataListener {
    private final SerialPort comPort;
    private final StringBuilder messageBuffer = new StringBuilder();

    public SerialManager(SerialPort comPort) {
        this.comPort = comPort;
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
                    char testChar = messages[i].charAt(0);
                    switch (testChar) {
                        case 'n':
                            sendMessage("bn");
                            break;
                        case 's':
                            sendMessage("bs");
                            break;
                    }
                }
                // Reset buffer with any remaining incomplete message
                messageBuffer.setLength(0);
                messageBuffer.append(messages[messages.length - 1]);
            }
        }

        String[] messages = messageBuffer.toString().split("\n");
        if (messages.length > 1) {

        }


    }

    public void sendMessage(String message) {
        comPort.writeBytes(message.getBytes(), message.length());
    }
}
