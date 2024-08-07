package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serializable;

public class BlockRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String block;
    public BlockRequest(String block) {
        this.block = block;
    }
    public String getBlock() {
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }
    public String toString() {
        return block;
    }
}
