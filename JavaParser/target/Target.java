import java.util.Vector;

// 연관 (Association)
class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Dog {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// 직접 연관 (Direct Association)
class Owner {
    private Person person;
    private Dog dog;

    public Owner(Person person, Dog dog) {
        this.person = person;
        this.dog = dog;
    }

    public Person getPerson() {
        return person;
    }

    public Dog getDog() {
        return dog;
    }
}

// 일반화 (Generalization)
class Animal {
    private String species;

    public Animal(String species) {
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }
}

class Cat extends Animal {
    public Cat() {
        super("Cat");
    }
}

class Elephant extends Animal {
    public Elephant() {
        super("Elephant");
    }
}

// 실체화 (Realization)
interface Arm {
    void move();
}

class HumanArm implements Arm {
    @Override
    public void move() {
        System.out.println("Human arm is moving.");
    }
}

class Nose implements Arm {
    @Override
    public void move() {
        System.out.println("Nose is sniffing.");
    }
}

// 의존 (Dependency)
class Action {
    public void performAction(Arm arm) {
        arm.move();
    }
}

// 집합 (Aggregation)
class Team {
    private String name;
    private Vector<Person> members;

    public Team(String name) {
        this.name = name;
        this.members = new Vector<>();
    }

    public void addMember(Person person) {
        members.add(person);
    }
}

// 합성 (Composition)
class Car {
    private Engine engine;

    public Car() {
        engine = new Engine();
    }

    public void start() {
        engine.start();
    }

    private class Engine {
        public void start() {
            System.out.println("Car's engine started.");
        }
    }
}

public class Target {
	   int a = 0;
		public void a() {
			System.out.println('1');
		}
		public void b() {
			System.out.println('1');
		}
		public void c() {
			System.out.println('1');
		}
		public void d() {
			System.out.println('1');
		}
		public void e() {
			System.out.println('1');
		}
		public void f() {
			System.out.println('1');
		}
		public void g() {
			System.out.println('1');
		}
		public void h() {
			System.out.println('1');
		}
		public void i() {
			System.out.println('1');
		}
		public void j() {
			System.out.println('1');
		}
		public void k() {
			System.out.println('1');
		}
		public void l() {
			System.out.println('1');
		}
		public void m() {
			System.out.println('1');
		}
		public void n() {
			System.out.println('1');
		}
		public void o() {
			System.out.println('1');
		}
		public void p() {
			System.out.println('1');
		}
		public void q() {
			System.out.println('1');
		}
		public void r() {
			System.out.println('1');
		}
		public void s() {
			System.out.println('1');
		}
		public void t() {
			System.out.println('1');
		}
		public void u() {
			System.out.println('1');
		}
		public void v() {
			System.out.println('1');
		}
		public void w() {
			System.out.println('1');
		}
		public void x() {
			System.out.println('1');
		}
		public void y() {
			System.out.println('1');
		}
		public void z() {
			System.out.println('1');
		}
	   public void gg(){
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");
	      System.out.println("g");      
	   }
}
//싱글톤 패턴 예제
public class Singleton {
  //싱글톤 객체를 static 변수로 선언
  private static Singleton instance;
  private int msg;
  
  //외부에서 생성자 호출 막기
  private Singleton(int msg) {
      this.msg = msg;
  }

  //인스턴스를 전달
  public static Singleton getInstance(int msg) {
     instance = new Singleton(msg);
      if (instance == null) {
          instance = new Singleton(msg);
      }
      return instance;
  }

  public void printMsg() {
      System.out.println(msg);
  }
}



/*
 * import java.util.Vector;
 * 
 * public class Target { public Human h; public Cat c; public Dog d; public
 * Elephant e;
 * 
 * public HumanArm tempMethod1(Arm a) { return (HumanArm) a; }
 * 
 * public Vector<Leg> tempMethod2(TwoLeg tl, FourLeg fl) { Vector<Leg> temp =
 * new Vector<Leg>(); temp.add(tl); temp.add(fl); return temp; }
 * 
 * public Arm[] tempMethod3(HumanArm ha, Nose n) { Arm [] temp = new Arm[2];
 * temp[0] = ha; temp[1] = n; return temp; } }
 * 
 * interface Leg{
 * 
 * }
 * 
 * interface Arm{
 * 
 * }
 * 
 * class HumanArm implements Arm{
 * 
 * }
 * 
 * class Nose implements Arm{
 * 
 * }
 * 
 * class TwoLeg implements Leg{
 * 
 * }
 * 
 * class FourLeg implements Leg{
 * 
 * }
 * 
 * class Animal { protected Leg leg; protected Arm arm; }
 * 
 * class Dog extends Animal{ public Dog() { this.leg = new FourLeg(); this.arm =
 * null; } }
 * 
 * class Cat extends Animal{ public Cat() { this.leg = new FourLeg(); this.arm =
 * null; } }
 * 
 * class Elephant extends Animal{ public Elephant() { this.leg = new FourLeg();
 * this.arm = new Nose(); } }
 * 
 * class Human extends Animal{ private Cat petCat; private Dog petDog; private
 * Elephant petElephant; public Human() { this.leg = new TwoLeg(); this.arm =
 * new HumanArm(); }
 * 
 * public Animal getPet(int i) { switch(i) { case 1: return petCat; case 2:
 * return petDog; case 3: return petElephant; default: return null; } } }
 */