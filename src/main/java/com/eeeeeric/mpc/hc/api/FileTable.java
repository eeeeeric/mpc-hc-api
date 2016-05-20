package com.eeeeeric.mpc.hc.api;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class represents the table of files returned by MPC-HC's web
 * interface file browser. Typically, this relates to a directory.
 */
public class FileTable
{
  private List<FileInfo> files;

  /**
   * Create a new FileTable object from a table element.
   *
   * @param element
   *        The table element
   */
  public FileTable(Element element)
  {
    files = new ArrayList<>();

    for (Element row : element.getElementsByTag("tr"))
    {
      if (row.getElementsByTag("th").isEmpty())
      {
        String name, href, type, size, date;
        boolean isDirectory = false;

        Elements columns = row.getElementsByTag("td");
        if (isDirectory(columns))
        {
          name = columns.get(0).getElementsByTag("a").first().text();
          href = columns.get(0).getElementsByTag("a").first().attr("href");
          type = columns.get(1).text();
          size = columns.get(2).text();
          date = columns.get(3).text();
          isDirectory = true;
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
  }

  /**
   * Check if a row is a directory.
   *
   * @param elements
   *        The row of elements
   *
   * @return {@code true} if the elements represent a directory,
   *         {@code false} otherwise
   */
  private boolean isDirectory(Elements elements)
  {
    for (Element element : elements)
    {
      if (element.className().equals("dirname"))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns a list of files in the table.
   *
   * @return a list of files in the table
   */
  public List<FileInfo> getFiles()
  {
    return files;
  }
}
