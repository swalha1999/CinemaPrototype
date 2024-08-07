package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class BlockResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String block;
    public BlockResponse(String block) {
        this.block = block;
    }
    public String getBlock() {
        return block;
    }
    public BlockResponse setBlock(String block) {
        this.block = block;
        return this;
    }
    public String toString() {
        return block;
    }
}
