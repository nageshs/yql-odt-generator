package net.nagiworld.odtgen;

/**
 * Created by IntelliJ IDEA.
* User: nageshs
* Date: Jun 19, 2009
* Time: 11:04:43 AM
* To change this template use File | Settings | File Templates.
*/
class Key {
  String name;
  String type = "xs:string";
  boolean required = false;

  Key(String key) {
    String[] vals = key.split(",");
    if (vals.length == 1) {
      this.name = vals[0];
      this.type = "xs:string";
    } else if (vals.length == 2) {
      this.name = vals[0];
      this.type = vals[1];
    } else if (vals.length == 3) {
      this.name = vals[0];
      this.type = vals[1];
      this.required = Boolean.valueOf(vals[2]);
    }
    //validate();
  }

  public String getName() {
    return name;
  }

  public boolean isRequired() {
    return required;
  }

  public String getType() {
    return type;
  }

}
