package il.cshaifasweng.OCSFMediatorExample.entities.messages;

import java.io.Serial;

public class BlockUserResponse implements Response {

    @Serial
    private static final long serialVersionUID = 1L;
    private String block;
    private String message;
    private Boolean success;

    public BlockUserResponse(String block) {
        this.block = block;
    }

    public String getBlock() {
        return block;
    }
    public BlockUserResponse setBlock(String block) {

        this.block = block;
        return this;
    }

    public String toString() {
        return block;
    }

    public String getMessage() {
        return this.message;
    }

    public BlockUserResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
