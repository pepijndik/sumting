package app.service.FileUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CSVHelper {

    private static final String TYPE = "text/csv";

    /**
     * Checks if the headers of the CSV file are valid
     *
     * @param headers the headers of the CSV file
     * @return int if the headers are valid and in what way:
     * -2 file contains invalid headers
     * -1 file contains some required headers
     * 0 file contains required and some optional headers
     * 1 file contains all required headers
     **/
    public static int headersValidation(String[] headers, String[] reqHeaders, String[] optHeaders) {
        int reqHeadersAmount = 0, optHeadersAmount = 0;
        for (String header : headers) {
            if (List.of(reqHeaders).contains(header)) reqHeadersAmount++;
            if (List.of(optHeaders).contains(header)) optHeadersAmount++;
            if (!List.of(reqHeaders).contains(header) && !List.of(optHeaders).contains(header))
                return -2;
        }
        if (reqHeadersAmount == reqHeaders.length && optHeadersAmount == optHeaders.length)
            return 1;
        else if (reqHeadersAmount == reqHeaders.length)
            return 0;
        else
            return -2;
    }

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
