package com.sun.imageio.spi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileCacheImageInputStreamFix;
import javax.imageio.stream.ImageInputStream;

public class InputStreamImageInputStreamFileCacheSpi extends InputStreamImageInputStreamSpi {

  // Assume the Sun's  InputStreamImageInputStreamSpi is registered first.
  // Register new implementation to overwrite previous registry entry.
  static {
    IIORegistry.getDefaultInstance().registerServiceProvider(new InputStreamImageInputStreamFileCacheSpi(),
        InputStreamImageInputStreamSpi.class);
  }

  public String getDescription(Locale locale) {
    return "Service provider that instantiates a FileCacheImageInputStream";
  }

  /**
   * Create an instance of a patched class. Ignore the argument useCache and always returns a
   * file-backed image stream.
   *
   * @param useCache - IGNORED
   * @return FileCacheImageInputStreamFix
   */
  public ImageInputStream createInputStreamInstance(Object input,
      boolean useCache,
      File cacheDir)
      throws IOException {
    if (input instanceof InputStream) {
      InputStream is = (InputStream) input;
      return new FileCacheImageInputStreamFix(is, cacheDir);

    } else {
      throw new IllegalArgumentException();
    }
  }
}
