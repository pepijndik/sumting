package app.server.Amazon;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class contains the names of the buckets
 */
@AllArgsConstructor
@Getter
public enum BucketNames {
    PROOF("sumtingproofs"),
    BASE("sumting");
    private final String bucketName;
}
