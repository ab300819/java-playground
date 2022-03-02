package org.netty.im.protocol;

/**
 * <p>请求信息体</p>
 *
 * @author mason
 * @date 2022/1/6 20:56
 */
public class MessageRequestPacket extends Packet {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
