package net.nagiworld.odtgen;

import com.sampullara.cli.Args;

/**
 * Created by IntelliJ IDEA.
 * User: nageshs
 * Date: Jun 18, 2009
 * Time: 3:00:04 PM
 */
public class Main {


  public static void main(String[] args) {
    OpenDataTableBean odtBean = new OpenDataTableBean();    
    if (args.length == 0) {
      Args.usage(odtBean);      
      return;
    }
    Args.parse(odtBean, args);
    System.out.println(odtBean.toXMLString());
  }
}
