package com.eeeeeric.mpc.hc.api;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
   * Parse a table element into a list of {@link FileInfo} objects.
   *
   * @param element
   *        A jsoup table element
   *
   * @return A list of {@link FileInfo} objects
   */
  static List<FileInfo> fromHTMLTableElement(Element element)
  {
    List<FileInfo> files = new ArrayList<>();

    for (Element row : element.getElementsByTag("tr"))
    {
      if (row.getElementsByTag("th").isEmpty())
      {
        String name, href, type, size, date;
        boolean isDirectory = false;
        Elements columns = row.getElementsByTag("td");

        for (Element e : columns)
        {
          if (e.className().equals("dirname"))
          {
            isDirectory = true;
            break;
          }
        }

        if (isDirectory)
        {
          name = columns.get(0).getElementsByTag("a").first().text();
          href = columns.get(0).getElementsByTag("a").first().attr("href");
          type = columns.get(1).text();
          size = columns.get(2).text();
          date = columns.get(3).text();
        }
        else
        {
          name = columns.get(0).getElementsByTag("a").first().text();
          href = columns.get(0).getElementsByTag("a").first().attr("href");
          type = columns.get(1).getElementsByTag("span").first().text();
          size = columns.get(2).getElementsByTag("span").first().text();
          date = columns.get(3).getElementsByTag("span").first().text();
        }

        files.add(new FileInfo(name, href, type, size, date, isDirectory));
      }
    }

    return files;
  }

  /**
   * Create a new FileInfo object.
   *
   * @param name
   *        The file name
   * @param href
   *        The hyperlink that can either be used to access a list of
   *        {@code FileInfo} objects for this directory (if this is
   *        a directory), or to play this file with MPC-HC
   * @param type
   *        The file type
   * @param size
   *        The file size
   * @param date
   *        The last modified date of the file
   * @param isDirectory
   *        True if this file is a directory
   *
   * @see MediaPlayerClassicHomeCinema#openFile(FileInfo)
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

  /**
   * Returns {@code true} if the file is a directory.
   *
   * @return {@code true} if the file is a directory.
   */
  public boolean isDirectory()
  {
    return isDirectory;
  }

  /**
   * Returns the name of the file.
   *
   * @return the name of the file
   */
  public String getFileName()
  {
    return name;
  }

  /**
   * Returns the relative hyperlink to the file.
   *
   * @return the relative hyperlink to the file
   */
  public String getHref()
  {
    return href;
  }

  /**
   * Returns the file type.
   *
   * @return the file type
   */
  public String getFileType()
  {
    return type;
  }

  /**
   * Returns the size of the file.
   *
   * @return the size of the file
   */
  public String getFileSize()
  {
    return size;
  }

  /**
   * Returns the timestamp of when this file was last modified.
   *
   * @return the timestamp of when this file was last modified
   */
  public String getLastModified()
  {
    return date;
  }
}
