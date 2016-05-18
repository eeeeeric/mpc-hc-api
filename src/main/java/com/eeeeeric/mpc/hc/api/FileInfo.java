package com.eeeeeric.mpc.hc.api;

/**
 * Contains information about a file that MPC-HC exposes.
 */
public class FileInfo
{
  private boolean isDirectory;
  private String name;
  private String href;
  private String type;
  private String size;
  private String date;

  /**
   * Create a new FileInfo object.
   *
   * @param name
   *        The file name
   * @param href
   *        The hyperlink that can either be used to access the {@FileTable}
   *        for this directory (if this is a directory), or to play this file
   *        with MPC-HC
   * @param type
   *        The file type
   * @param size
   *        The file size
   * @param date
   *        The last modified date of the file
   * @param isDirectory
   *        True if this file is a directory
   */
  public FileInfo(String name, String href, String type, String size,
          String date, boolean isDirectory)
  {
    this.name = name;
    this.href = href;
    this.type = type;
    this.size = size;
    this.date = date;
    this.isDirectory = isDirectory;
  }

  public boolean isDirectory()
  {
    return isDirectory;
  }

  public String getName()
  {
    return name;
  }

  public String getHref()
  {
    return href;
  }

  public String getType()
  {
    return type;
  }

  public String getSize()
  {
    return size;
  }

  public String getDate()
  {
    return date;
  }
}
