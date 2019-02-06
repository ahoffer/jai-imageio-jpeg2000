package javax.imageio.stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class FileCacheImageInputStreamFix extends FileCacheImageInputStream {

  Field fieldObj;

  public FileCacheImageInputStreamFix(InputStream stream, File cacheDir)
      throws IOException {
    super(stream, cacheDir);
    try {
      fieldObj = getClass().getSuperclass().getDeclaredField("length");
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    fieldObj.setAccessible(true);
  }

  public long length() {
    try {
      return readLength();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  protected Long readLength() throws IllegalAccessException {
    Object length = new Object();
    return fieldObj.getLong(length);

  }


}
