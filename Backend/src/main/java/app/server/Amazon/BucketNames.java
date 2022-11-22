package app.server.Amazon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketNames {
    PROOF("sumtingproofs"),
    BASE("sumting");
    private final String bucketName;
}
