package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;
import java.io.Serializable;

public class BlockResponse implements Response {

    @Serial
    private static final long serialVersionUID = 1L;
    private String block;
    private String message;
    private Boolean success;

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

    public String getMessage() {
        return this.message;
    }

    public BlockResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
