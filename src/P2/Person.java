package P2;

/**
 * 表示人的类，每个人有代表自己的名字
 * 不可变类型
 */
public class Person {
    private final String name;

    // Abstraction function:
    //   AF(name) = 名字为name的人
    // Representation invariant:
    //   name为非空字符串
    // Safety from rep exposure:
    //   所有属性均为private final
    //   name为String类型，为不可变类型

    /**
     * 创建一个名字为name的人
     * @param name 非空字符串
     */
    public Person(String name) {
        this.name = name;
        //checkRep();
    }

    /**
     * 检查是否符合rep
     */
    public void checkRep(){
        assert !name.equals(""):"人名为空";
    }

    /**
     * 获取人名
     * @return 人名
     */
    public String getName() {
        return name;
    }

    /**
     * 判断相等
     * @param obj 判断对象
     * @return 如果同为Person且名字相同则返回true
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Person)){
            return false;
        }
        if(obj == this) return true;
        return name.equals(((Person) obj).getName());
    }

    /**
     * 哈希码获取
     * @return 一个Person的哈希码
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result;
        result = prime + ((name == null)? 0 : name.hashCode());
        return result;
    }
}
