Android Clean Code
=======================

Apresentação sobre melhoraria de produtividade no desenvolvimento de aplicativos Android.

<h2>Boilerplate code:</h2>

Seção de código que é incluído em vários lugares com pouca ou nenhuma alteração. Muito verboso, o programador acaba escrevendo muito código a fim de fazer pouca coisa.

Exemplo:
```java
  public class MyActivity extends Activity {
  
      private TextView label;
  
      private Drawable image;
  
      private SearchManager searchManager;
  
      @Override
      public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.myactivity);
          this.label = (TextView) findViewById(R.id.mylabel);
          this.image = getResources().getDrawable(R.drawable.myimage);
          this.searchManager = (SearchManager) getSystemService(Activity.SEARCH_SERVICE);
      }
  }

```


<h2>Annotation</h2>

Definição: Annotation é um tipo de metadado. Em Java, uma Annotation é uma forma sintática de metadado que pode ser adicionada ao código: Classes, métodos, variáveis, parametros e pacotes. As annotations podem: ser embutidas em arquivos .class gerados pelo compilador e mantidos pela JVM e então influenciar durante a execução; Informar ao compilador a respeito de algo; e Compile-time and deployment-time processing

Exemplo

Algumas anotações padrões do Java:

<ul>
<li>@Override</li>
<li>@Deprecated </li>
<li>@SuppressWarnings</li>
</ul>

Um exemplo do JPA/Hibernate:

Classe de Modelo:
```java
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
 
@Entity
@Table(name = "usr") // @Table is optional, but "user" is a keyword in many SQL variants 
public class User {
    @Id // @Id indicates that this it a unique primary key
    @GeneratedValue // @GeneratedValue indicates that value is automatically generated by the server
    private Long id;
 
    @Column(length = 32, unique = true)
    // the optional @Column allows us makes sure that the name is limited to a suitable size and is unique
    private String name;
 
    // note that no setter for ID is provided, Hibernate will generate the ID for us
 
    public long getId() {
        return id;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
}
```

Classe de Controle:
```java
   public void testNewUser() {
 
        EntityManager entityManager = Persistence.createEntityManagerFactory("tutorialPU").createEntityManager();
 
        entityManager.getTransaction().begin();
 
        User user = new User();
 
        user.setName(Long.toString(new Date().getTime()));
 
        entityManager.persist(user);
 
        entityManager.getTransaction().commit();
 
        // see that the ID of the user was set by Hibernate
        System.out.println("user=" + user + ", user.id=" + user.getId());
 
        User foundUser = entityManager.find(User.class, user.getId());
 
        // note that foundUser is the same instance as user and is a concrete class (not a proxy)
        System.out.println("foundUser=" + foundUser);
 
        assertEquals(user.getName(), foundUser.getName());
 
        entityManager.close();
    }
```java

JUnit 4:

@Before
@BeforeClass
@After
@AfterClass
@Test
@Ignore
@Test(timeout=500)
@Test(expected=IllegalArgumentException.class)
<ul>
<li>@Override</li>
<li>@Deprecated</li>
<li>@SuppressWarnings</li>
<li>@SuppressWarnings</li>
</ul>

```java
public class JunitTest1 {
 
    private Collection collection;
 
    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code   
      System.out.println("@BeforeClass - oneTimeSetUp");
    }
 
    @AfterClass
    public static void oneTimeTearDown() {
        // one-time cleanup code
    	System.out.println("@AfterClass - oneTimeTearDown");
    }
 
    @Before
    public void setUp() {
        collection = new ArrayList();
        System.out.println("@Before - setUp");
    }
 
    @After
    public void tearDown() {
        collection.clear();
        System.out.println("@After - tearDown");
    }
 
    @Test
    public void testEmptyCollection() {
        assertTrue(collection.isEmpty());
        System.out.println("@Test - testEmptyCollection");
    }
 
    @Test
    public void testOneItemCollection() {
        collection.add("itemA");
        assertEquals(1, collection.size());
        System.out.println("@Test - testOneItemCollection");
    }
}
```


<h2>Inverse of Control & Dependency Injection</h2>

Os padrões de  Inversion of Control (IoC) e Dependency Injection(DI) são todos sobre a remoção de dependências no código.

Inversion of Control(IoC) significa que o fluxo de execução não é feito pelo programador, e sim pelo framework em questão. O framework chama o programador, ao invés do inverso.

Exemplo:

Sem IoC:
```ruby
puts 'What is your name?'
name = gets
process_name(name)
puts 'What is your quest?'
quest = gets
process_quest(quest)
```

Com IoC:
```ruby
require 'tk'
root = TkRoot.new()
name_label = TkLabel.new() {text "What is Your Name?"}
name_label.pack
name = TkEntry.new(root).pack
name.bind("FocusOut") {process_name(name)}
quest_label = TkLabel.new() {text "What is Your Quest?"}
quest_label.pack
quest = TkEntry.new(root).pack
quest.bind("FocusOut") {process_quest(quest)}
Tk.mainloop()
```

Dependency Injection(DI) significa dar um objeto suas variáveis de instância.

Exemplo:

Sem DI:
```java
public class Example { 
  private DatabaseThingie myDatabase; 

  public Example() { 
    myDatabase = new DatabaseThingie(); 
  } 

  public void DoStuff() { 
    ... 
    myDatabase.GetData(); 
    ... 
  } 
}
```

Com DI:
```java
public class Example { 
  private DatabaseThingie myDatabase; 

  public Example() { 
    myDatabase = new DatabaseThingie(); 
  } 

  public Example(DatabaseThingie useThisDatabaseInstead) { 
    myDatabase = useThisDatabaseInstead; 
  }

  public void DoStuff() { 
    ... 
    myDatabase.GetData(); 
    ... 
  } 
}
```

Utilização do padrão de DI, em testes por exemplo:
```java
public class ExampleTest { 
  TestDoStuff() { 
    MockDatabase mockDatabase = new MockDatabase(); 

    // MockDatabase is a subclass of DatabaseThingie, so we can 
    // "inject" it here: 
    Example example = new Example(mockDatabase); 

    example.DoStuff(); 
    mockDatabase.AssertGetDataWasCalled(); 
  } 
}
```
E no Android?

IoC:

Todo o padrão de Activites, definido no Manifest, e de Views através do setContentView e findById

DI:

public class MainApplication extends Application  {

    private MyService service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = new MyServiceImpl();
    }

    public MyService getMyService() {
        return this.service;
    }
}

public class MainActivity extends Activity  {

    private MyService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication app = (MainApplication) getApplication();
        service = app.getMyService();
    }
}



Fontes:
http://martinfowler.com/bliki/InversionOfControl.html
http://martinfowler.com/articles/injection.html#InversionOfControl
http://stackoverflow.com/questions/130794/what-is-dependency-injection
http://www.jamesshore.com/Blog/Dependency-Injection-Demystified.html


4 - Abordar isso no Android. Apresentar o AndroidAnnotations e o RoboGuice

Mostrar exemplos do link, parte “The Android Way”: http://blog.springsource.org/2011/08/26/clean-code-with-android/ 

Apresentar o RoboGuice, um framework de DI para Android, e mostrar um exemplo

Apresentar o AndroidAnnotations, um framework cujo objetivo é reduzir código boilerplate com o uso de Annotations, e mostrar um exemplo.

Uma comparação entre os 2 frameworks(Pode ser uma tabela ou uma lista) e explicar pq escolhermos o AndroidAnnotations.  

Acho que aqui já poderíamos explicar o básico do Annotations... 
Ou vc acha que primeiro mostramos o código todo (seção 5) e depois mostramos como vc tem que estruturar seu projeto para que o framework funcione? (exemplo: seu Manifest possuirá MyActivity_ em vez de MyActivity) Não sei qual seria a melhor ordem de apresentação...

Boa! A gente explica o framework e mostrar exemplos do próprio site/wiki dele.
Depois, a gente mostra o nosso projeto(arquivo Manifest, classes e interfaces) e como foi estruturado o nosso projeto para funcionar com o framework.

5 - Mostrar projeto

Quem começou?
http://www.ebusinessinformation.fr/
Pagina:
https://github.com/excilys/androidannotations
Wiki bem completa:
https://github.com/excilys/androidannotations/wiki
Cookbook (exemplos prontos):
https://github.com/excilys/androidannotations/wiki/Cookbook
Licença:
Apache 2

Exibir código do projeto exemplo, passando pelas classes e interfaces.

6 - Fim