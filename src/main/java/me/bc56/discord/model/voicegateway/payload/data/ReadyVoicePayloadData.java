package me.bc56.discord.model.voicegateway.payload.data;

import com.google.gson.annotations.SerializedName;
import me.bc56.discord.util.Constants;

// https://discord.com/developers/docs/topics/voice-connections#establishing-a-voice-websocket-connection-example-voice-ready-payload
public class ReadyVoicePayloadData implements VoiceGatewayPayloadData {
    public transient static final int opCode = Constants.VoiceGatewayPayloadType.READY;

    int ssrc;

    String ip;

    int port;

    String[] modes;

    //heartbeat_interval (this can be ignored)

    @Override
    public int getOpCode() {
        return 0;
    }

    public int getSsrc() {
        return ssrc;
    }

    public void setSsrc(int ssrc) {
        this.ssrc = ssrc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String[] getModes() {
        return modes;
    }

    public void setModes(String[] modes) {
        this.modes = modes;
    }
}
