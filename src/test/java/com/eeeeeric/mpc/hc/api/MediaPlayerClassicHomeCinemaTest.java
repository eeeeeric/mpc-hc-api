package com.eeeeeric.mpc.hc.api;

import java.net.URLEncoder;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Contains tests for {@link MediaPlayerClassicHomeCinema}.
 */
@Test
public class MediaPlayerClassicHomeCinemaTest
{
  private MediaPlayerClassicHomeCinema mpc;
  private String hostname = System.getProperty("mpchcHostname");
  private Integer port = Integer.getInteger("mpchcPort");
  // This should be an absolute path like "C:\Users\User\file.mkv"
  private String mediaFileOnHost = System.getProperty("absolutePathToMediaFileOnHost");

  /**
   * Initialize the {@link MediaPlayerClassicHomeCinema} instance,
   * throwing a {@link SkipException} if connection information
   * was not found. Because of this, it is unlikely these tests can be
   * automated, and will need to be run manually.
   */
  @BeforeClass
  public void init()
  {
    if (hostname == null || port == null)
    {
      throw new SkipException("Did not find connection information for MPC-HC");
    }
    else if (mediaFileOnHost == null)
    {
      throw new SkipException("A media file must be specified");
    }
    mpc = new MediaPlayerClassicHomeCinema(hostname, port);
  }

  /**
   * Play a video and perform some basic remote controls.
   *
   * @throws Exception
   */
  @Test
  public void test() throws Exception
  {
    String parentDir = mediaFileOnHost.substring(
            0, mediaFileOnHost.lastIndexOf('\\'));
    String fileName = mediaFileOnHost.substring(
            mediaFileOnHost.lastIndexOf('\\') + 1);
    FileTable fileTable = mpc.browse("/browser.html?path="
            + URLEncoder.encode(parentDir, "UTF-8"));
    for (FileInfo file : fileTable.getFiles())
    {
      if (file.getFileName().equals(fileName))
      {
        mpc.openFile(file);
        break;
      }
    }

    Thread.sleep(1000*5L);
    Assert.assertTrue(mpc.isPlaying());
    mpc.setMute(true);
    Assert.assertTrue(mpc.isMuted());
    mpc.toggleMute();
    Assert.assertFalse(mpc.isMuted());

    mpc.setVolume(50);
    Assert.assertEquals(mpc.getVolume(), 50);
    mpc.pause();
    Assert.assertTrue(mpc.isPaused());
    TimeCode fiveMinutes = new TimeCode("00:05:00");
    mpc.seek(fiveMinutes);
    Assert.assertEquals(mpc.getPosition(), fiveMinutes);
    mpc.play();
    Assert.assertTrue(mpc.isPlaying());
  }
}
