package bean;

public class Word {
	  //关键词
	  private String key;

	  //统计的关键字次数 
	  private int countNum;

	  public Word(String key, int countNum) {
	    this.key = key;
	    this.countNum = countNum;
	  }

	  public String getKey() {
	    return key;
	  }

	  public void setKey(String key) {
	    this.key = key;
	  }

	  public int getCountNum() {
	    return countNum;
	  }

	  public void setCountNum(int countNum) {
	    this.countNum = countNum;
	  }

	  
}
