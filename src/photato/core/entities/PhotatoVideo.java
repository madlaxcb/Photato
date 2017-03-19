package photato.core.entities;

import com.google.gson.annotations.Expose;
import java.nio.file.Path;
import photato.Routes;
import photato.core.metadata.Metadata;
import photato.helpers.FileHelper;
import photato.helpers.Md5;

public class PhotatoVideo extends PhotatoMedia {

    @Expose
    public final String videoType;
    
    @Expose
    public final String videoPath;

    public PhotatoVideo(Path rootFolder, Path path, Metadata metadata, PictureInfos thumbnailInfos, PictureInfos fullScreenInfos, long lastModificationTimestamp) {
        super("video", rootFolder, path, metadata, thumbnailInfos, fullScreenInfos, lastModificationTimestamp);

        if (this.filename.length() > 40 || this.filename.contains("_") || this.filename.toLowerCase().startsWith("vid")) {
            this.name = path.getParent().getFileName().toString();
        } else {
            this.name = path.getParent().getFileName() + "/" + this.filename;
        }

        this.videoType = "video/" + FileHelper.getExtension(path.toString()).toLowerCase();
        this.videoPath = Routes.rawVideosRootUrl + "/" + this.path;
    }

    public static Path getExtractedPicturePath(Path extractedVideoPicturesFolders, Path videoFsPath, long videoLastModificationTimestamp) {
        String src = videoFsPath + "_" + videoLastModificationTimestamp;
        return extractedVideoPicturesFolders.resolve(Md5.encodeString(src) + ".jpg");
    }

}
