import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.MemoryManagerMXBean;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

public class parser {
   public CompilationUnit compilationUnit;

   public parser() {
      String fileName = "../target";
      File ffile = new File(fileName);
      File[] fileArray = ffile.listFiles();

      if (ffile.listFiles() != null) {
         for (int i = 0; i < fileArray.length; i++) {
            fileName = fileArray[i].toString();
            if (!fileArray[i].isFile()) {
               System.out.println(fileArray[i].getAbsolutePath() + "does not exist.");
               System.exit(1);
            }
            else {
               System.out.println("\n"+fileArray[i] + " load complete!");
               parse(fileArray[i]);
            }
         }
         // System.out.println("digraph packageName{");
         /*for (int i = 0; i < fileArray.length; i++) {
            fileName = fileArray[i].toString();
            System.out.println("load: " + fileArray[i]);
            parse(fileArray[i]);
         }*/
         // System.out.println("}");
      } else {
         System.out.println("load: " + fileName);
         parse(ffile);
      }
   }

   public void parse(File file) {
      BufferedReader reader = null;
      try {
         reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

      StringBuilder source = new StringBuilder();
      char[] buf = new char[50];
      int numRead = 0;

      try {
         while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            source.append(readData);
            buf = new char[1024];
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      // 파일 읽기 끝 옵션 설정 시작
      ASTParser parser = ASTParser.newParser(AST.JLS8);
      parser.setKind(ASTParser.K_COMPILATION_UNIT);
      parser.setSource(source.toString().toCharArray());
      parser.setStatementsRecovery(true);
      parser.setBindingsRecovery(true);
      parser.setResolveBindings(true);
      // 옵션끝
      Map<?, ?> options = JavaCore.getOptions();
      JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
      parser.setCompilerOptions(options);
      compilationUnit = (CompilationUnit) parser.createAST(null); // AST 전체 저장
      // 여기서부터 코딩 시작
      startParser();
   }

   public void startParser() {
      List<AbstractTypeDeclaration> types = compilationUnit.types();
      for (AbstractTypeDeclaration type : types) {
         if (type instanceof TypeDeclaration) {
            typeDeclaration((TypeDeclaration) type);
         }
      }
   }

   // TYPE
   public void typeDeclaration(TypeDeclaration type) {
      System.out.println("\n<< Class: " + type.getName().toString() +" >>");
      for (int i = 0; i < type.modifiers().size(); i++) {
         String typeModifier = type.modifiers().get(i).toString();
         boolean type_mo = false;
         if (typeModifier != null) {
            if (typeModifier.equals("abstract")) { type_mo = true; }
            else { System.out.println("modifier: " + typeModifier);}
         }
         boolean typeinterface = type.isInterface();
         System.out.println("interface: " + typeinterface);
         if(type.getSuperclassType()!=null) {
        	 String typesuperclass = type.getSuperclassType().toString();
        	 System.out.println("superclass: "+typesuperclass);
         }
      }
      for (Object bodyDeclaration : type.bodyDeclarations()) {
         if (bodyDeclaration instanceof FieldDeclaration) {
            System.out.println("=Field=");
            fieldDeclaration(bodyDeclaration);
         } else if (bodyDeclaration instanceof MethodDeclaration) {
            System.out.println("=Method=");
            methodDeclaration(bodyDeclaration);
         }
      }
   }

   // FIELD
   public void fieldDeclaration(Object bodyDeclaration) {
      FieldDeclaration field = (FieldDeclaration) bodyDeclaration;
      //field type
      String fieldType = field.getType().toString();
      System.out.println("type: " + fieldType);
      try {
         //getFragment 함수 호출 
    	  
    	  
         get_Fragment(field.fragments().get(0));
      } catch (Exception e) {
         throw e;
      }
   }

   // METHOD
   public void methodDeclaration(Object bodyDeclaration) {
      MethodDeclaration method = (MethodDeclaration) bodyDeclaration;
      //Method modifier
      for (int i = 0; i < method.modifiers().size(); i++) {
         String methodModifier = method.modifiers().get(i).toString();
         System.out.print("modifier: ");
         if (methodModifier != null)
            System.out.println(methodModifier);
      }
      //Method ReturnType
      if(method.getReturnType2()!=null) {
         String returntype = method.getReturnType2().toString();
         System.out.println("returntype: " + returntype);      
      }
      //Method Name
      String name = method.getName().toString();
      System.out.println("name: " + name);
      
      //method parameter
      List<SingleVariableDeclaration> ps = method.parameters();
      for (SingleVariableDeclaration p : ps) {
         System.out.println("*parameter" + "\ntype: " + p.getType() + "\nname: " + p.getName());
      }
      
      get_Block(method.getBody());
   }

   // Body내의 Statements 종류에 따라 출력하는 함수
   public void get_Statement(Object o) {
      
      //VariableDeclarationStatement (변수선언)
      if (o instanceof VariableDeclarationStatement) {
         VariableDeclarationStatement vds = (VariableDeclarationStatement) o;
         for (int i = 0; i < vds.modifiers().size(); i++) {
             String Modifier = vds.modifiers().get(i).toString();
             System.out.print("modifier: ");
             if (Modifier != null)
                System.out.println(Modifier);
          }
         System.out.println("type: " + vds.getType().toString());
         get_Fragment(vds.fragments().get(0));
         
      //ExpressionStatement (대입식)
      } else if (o instanceof ExpressionStatement) {
         ExpressionStatement es = (ExpressionStatement) o;
         get_Assignment(es.getExpression());
         
      // ForStatement (For문)
      } else if (o instanceof ForStatement) {
         ForStatement fs = (ForStatement) o;
         System.out.println("*for문");
         System.out.println("- 초기식");
         //Expression e = (Expression)fs.initializers().get(0);
         //get_Expression(e);
         VariableDeclarationExpression vde = (VariableDeclarationExpression) fs.initializers().get(0);
         get_Fragment(vde.fragments().get(0));
         
         System.out.println("- 조건식");
         InfixExpression ie = (InfixExpression)fs.getExpression();
         get_Expression(ie);
         
         System.out.println("- 증감식");
         get_Assignment(fs.updaters().get(0));
         
         System.out.println("- 실행문");
         get_Block(fs.getBody());
         
         // IfStatement (If문)
      } else if (o instanceof IfStatement) {
         IfStatement is = (IfStatement) o;
         
         System.out.println("*if문");
         System.out.println("- 조건문");
         get_Expression(is.getExpression());
         
         System.out.println("- 실행문");
         get_Block(is.getThenStatement());
         
         if(is.getElseStatement()!=null) {
        	 System.out.println("else 실행문");
             get_Block(is.getElseStatement()); 
         }         
      } else if(o instanceof WhileStatement) {
    	  WhileStatement ws = (WhileStatement) o;
    	  System.out.println("*while 조건문");
    	  get_Expression(ws.getExpression());
    	  
    	  System.out.println("- while 실행문");
    	  get_Block(ws.getBody());
      }
   }
   
   public void get_Block(Object o) {
      //{중괄호}가 있는 경우 Block이 생성되므로 내부에 있는 statements들을 하나씩 분석
      if (o instanceof Block) {
         Block b = (Block)o;
         List<Statement> ls = b.statements();
         for (Statement s:ls) {
            get_Statement(s);
         }
      }
      //{중괄호}가 없는 경우 Block이 생성되지않으므로 바로 statement 분석
      else if (o instanceof Statement)
         get_Statement(o);
      else
         System.out.println();
   }
   
   // Fragment (변수 선언 또는 대입식 또는 연산식)
   public void get_Fragment(Object o) {
      if (o instanceof VariableDeclarationFragment) {
         VariableDeclaration vdf = (VariableDeclarationFragment) o;
         String fieldName = vdf.getName().toString().replaceAll(" ", "");

         // 연산식일 경우
         if (vdf.getInitializer() instanceof InfixExpression) {
            // InfixExpression(연산식)을 사용하기 위해 getExpression() 함수 호출
            System.out.println("name: " + fieldName);
            get_Expression(vdf.getInitializer());
         }
         else if(vdf.getInitializer() instanceof ClassInstanceCreation) {
            ClassInstanceCreation cic = (ClassInstanceCreation)vdf.getInitializer();
            System.out.println("객체 name: " + fieldName);
            System.out.println("객체 type: "+cic.getType());
         }
         // 값을 대입할 경우
         else if (vdf.getInitializer()!=null){
            System.out.println("name: " + fieldName);
            String fieldToken = vdf.getInitializer().toString();
            System.out.println("value: " + fieldToken);
         }
         else {
            System.out.println("name: " + fieldName);
         }
      }
   }
   
   public void get_Expression(Object o) {
      if (o instanceof InfixExpression) {
         InfixExpression ie = (InfixExpression) o;
         String left2 = ie.getLeftOperand().toString();
         String oper2 = ie.getOperator().toString();
         String right2 = ie.getRightOperand().toString();

         System.out.println("left_operand: " + left2 + "\noperation: " + oper2 + "\nright_operand: " + right2);
      }else {
         System.out.println("right_hand_side: "+o.toString());
      }
   }
   
   public void get_Assignment(Object o) {
      if (o instanceof Assignment) {
         Assignment ag = (Assignment)o;
         String left1 = ag.getLeftHandSide().toString().replace(" ", "");
         String oper1 = ag.getOperator().toString().replace(" ", "");
         System.out.println("left_hand_side: " + left1 + "\noperation: " + oper1);

         Expression ife = (Expression) ag.getRightHandSide();
         get_Expression(ife);            
      }
      else if (o instanceof MethodInvocation) {
         MethodInvocation mi = (MethodInvocation) o;
         System.out.println("expression: "+mi.getExpression());   //TODO: System 과 out 분리 못함
         System.out.println("name: "+mi.getName().getIdentifier());
         for (int i=0; i<mi.arguments().size(); i++) {
            System.out.println("argument: "+mi.arguments().get(i));         
         }
      }
   }
}
