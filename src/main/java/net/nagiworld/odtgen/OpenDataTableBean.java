package net.nagiworld.odtgen;

import com.sampullara.cli.Argument;
import org.antlr.stringtemplate.StringTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nageshs
 * Date: Jun 18, 2009
 * Time: 5:46:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenDataTableBean {

  private static final String template;
  private static final String LINE_SEP = System.getProperty("line.separator");

  private static final Set<String> ALLOWED_BINDINGS =
          new HashSet<String>(Arrays.asList("insert", "update", "delete", "select"));
  
  private String binding;
  private String author;
  private String sampleQuery;
  private String itemPath;
  private List<Key> keys;  

  private StringTemplate st;

  public OpenDataTableBean() {
    st = new StringTemplate(template);
  }

  public String getAuthor() {
    if (author == null) author = System.getProperty("user.name");
    return author;
  }

  @Argument (description = "Sets the Author of the ODT. Defaults to user.name ")
  public void setAuthor(String author) {
    this.author = author;
  }

  public String getBinding() {
    return binding;
  }

  @Argument (required = true, alias = "b", description="Binding such as insert, update, select, delete")
  public void setBinding(String binding) {
    binding = binding.toLowerCase();
    this.binding = binding;
  }

  public String getItemPath() {
    return itemPath;
  }
  @Argument (description = "itemPath associated with the binding")
  public void setItemPath(String itemPath) {
    this.itemPath = itemPath;
  }

  public String getSampleQuery() {
    return sampleQuery;
  }

  @Argument (alias = "sample", description = "A sample Query for the binding")
  public void setSampleQuery(String sampleQuery) {
    this.sampleQuery = sampleQuery;
  }

  public Key[] getKeys() {
    return (Key[]) keys.toArray();
  }

  @Argument (alias = "k", description="add a key format -k {name},{type},{required} e.g.: -k query,xs:string,true")
  public void setKey(String key) {
    if (keys == null) keys = new ArrayList<Key>();
    Key k = new Key(key);
    keys.add(k);
  }

  public String toXMLString() {
    if (!ALLOWED_BINDINGS.contains(binding)) {
      throw new IllegalArgumentException("Invalid binding '" + binding +
              "'. Expecting one of "+ALLOWED_BINDINGS);
    }
    if ("select".equals(getBinding())) {
      st.setAttribute("paging", true);
    }
    st.setAttribute("binding", getBinding());
    st.setAttribute("author", getAuthor());
    st.setAttribute("sampleQuery", getSampleQuery());
    st.setAttribute("itemPath", getItemPath());
    if (keys != null && keys.size() > 0) {
      st.setAttribute("keys", keys);
    }
    return st.toString();
  }


  static {
    try {
      template = makeTemplate();
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private static String makeTemplate() throws IOException {
    //System.out.println(new Object().getClass().getResource("/net/nagiworld/odtgen/templates/template.st"));
    InputStream is = Main.class.getResourceAsStream("/net/nagiworld/odtgen/templates/template.st");
    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    String line = null;
    StringBuilder sb = new StringBuilder();
    while ((line = reader.readLine()) != null) {
      sb.append(line);
      sb.append(LINE_SEP);
    }
    return sb.toString();
  }

}
