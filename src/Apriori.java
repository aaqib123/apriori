
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Apriori {
    
    static ArrayList<String> rules = new ArrayList<String>(); 
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException  {
        
            Scanner s111=new Scanner(System.in);
            System.out.println("What is the name of the file containing your data?");	
            String file1=s111.next();
            File f1 = new File(file1);
            try {
                Scanner s2=new Scanner(f1);
            } catch (FileNotFoundException ex) {
               System.out.println("File not Found");
               System.exit(0);
            }
            System.out.println("Please select the minimum support rate(0.00-1.00):");	
            double sup=s111.nextDouble();
                while(sup < 0 || sup >1){
                    System.out.println("Invalid choice, Enter Again");
                     sup=s111.nextDouble();
                }
            System.out.println("Please select the minimum confidence rate(0.00-1.00):");	
            double conf = s111.nextDouble();
                while(conf < 0 || conf >1){
                    System.out.println("Invalid choice, Enter Again");
                     conf=s111.nextDouble();
                }
          /*  double sup = 0.3;
            double conf = 0.4;
            String file1 = "data2";*/
            // ----------------------------------------------------  
            //file to numerical
            String TheFile = copyFile(file1);
            //
            //
            numerify(TheFile);//convert database to numerical format
            File f2= new File(TheFile);
             // ----------------------------------------------------  
            //row and col count
            int rows = row(f2);
            //System.out.println(rows); 
            int cols = col(f2);
            //System.out.println(cols); 
             // ----------------------------------------------------   
            ArrayList<String> freqsets = new ArrayList<String>();
            
            //create columns to  single rows
            String line = Createitem(f2,rows,cols);
            // A to A,1
            String items =  itemset1(line,rows,cols);
            String[] Itemset = itemcount(items,rows,cols,sup);
            for(int i=0;i<Itemset.length;i++){freqsets.add(Itemset[i]);}
            String[] Itemset2 = itemset2(Itemset,rows,cols,f2,sup);
            for(int i=0;i<Itemset2.length;i++){freqsets.add(Itemset2[i]);}
             String[] Itemsetkk = nitemset(Itemset2,rows,cols,f2,sup);
            for(int i=0;i<Itemsetkk.length;i++){freqsets.add(Itemsetkk[i]);}
             String[] Itemsetk = nitemset4(Itemsetkk,rows,cols,f2,sup);
            for(int i=0;i<Itemsetk.length;i++){freqsets.add(Itemsetk[i]);}
           
            System.out.println("freq itemsets");for(String k : freqsets){  System.out.println(k);  } System.out.println("freq itemsets count :"+ freqsets.size());  
            
        // ----------------itemsets apart is done------------------------------------     

            Set<String> s3 = new HashSet<String>();
        
        for(int i=0;i<Itemset2.length;i++){
            
            /*   System.out.println("--------------------------------"+Itemset2[i]);
               String[] set = Itemset2[i].split(",");*/
               
               String[] set = {"<1,1>","<1,2>","<2,2>"};
               s3 =  subsetG(set);
               pruneit(s3,su2.get(i),f2,rows,conf);
               
           }
           
        Set<String> s1 = new HashSet<String>();
        
        for(int i=0;i<Itemsetkk.length;i++){
               String[] set = Itemsetkk[i].split(", ");
               s1 =  subsetG(set);
               pruneit(s1,su.get(i),f2,rows,conf);
               
           }
        
        Set<String> s = new HashSet<String>();
        for(int i=0;i<Itemsetk.length;i++){
               String[] set = Itemsetk[i].split(", ");
               s =  subsetG(set);
               pruneit(s,suppp.get(i),f2,rows,conf);
           }
          
        for(int i=0;i<rules.size();i++){  System.out.println(rules.get(i));  }System.out.println("Rules count :"+ rules.size());   
        
         torulefile(sup,conf,rows);
         File x = new File("temp.txt");
         if(x.exists()){
             textH(x,names);
             textd(x,names1);
         } 
         torulefilestart(sup,conf,rows);
         mix();
        
         File fq= new File("temp.txt");
          fq.delete();
     
        
    }//main ends---------------------------------------------------------------------------------------------------------------------------------------

    
    private static void mix() throws IOException {
         
            Path path = Paths.get("Rule.txt");
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);
 
        
        Path path1 = Paths.get("temp.txt");
        Charset charset1 = StandardCharsets.UTF_8;
        String content1 = new String(Files.readAllBytes(path1), charset1);
       
        content = content.concat(content1);
        Files.write(path, content.getBytes(charset));
        
         }
     //*******************************************************************************************************************     
    private static void textH(File x, List names ) throws FileNotFoundException, IOException{
        
     //   System.out.println(names);  
        Scanner z = new Scanner(x);
        
         Path path = Paths.get("temp.txt");
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);
        for(int i=0;i<names.size();i++){
            
        String la = (names.get(i)).toString();
        String[] l1 = la.split("\\.");
       // System.out.println(la);
                
          for(int j=0;j<l1.length;j++){
             //  System.out.println(l1[j]);  
          }
                content = content.replaceAll("\\[|\\]", " ");
                 Files.write(path, content.getBytes(charset));
             content = content.replaceAll(" ","");
             Files.write(path, content.getBytes(charset));
              content = content.replaceAll("->"," -> ");
             Files.write(path, content.getBytes(charset));
                  content = content.replaceAll(","," ; ");
             Files.write(path, content.getBytes(charset));
              content = content.replaceAll(";"," ; ");
          Files.write(path, content.getBytes(charset));
  
                content = content.replaceAll(l1[0]+".",l1[1]+".");
                  Files.write(path, content.getBytes(charset));
          
    }
    }
      //*******************************************************************************************************************     
   private static void textd(File x, List names ) throws FileNotFoundException, IOException{
        
      //  System.out.println(names);  
        Scanner z = new Scanner(x);
        
         Path path = Paths.get("temp.txt");
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path), charset);
        for(int i=0;i<names.size();i++){
            
        String la = (names.get(i)).toString();
        String[] l1 = la.split("\\.");
       // System.out.println(la);
                
          for(int j=0;j<l1.length;j++){
             //  System.out.println(l1[j]);  
          }
          
           int x11 = Integer.parseInt(l1[0]);
           
         //   System.out.println(x11);
           if(x11>9){
             content = content.replace("."+x11+" ", "."+l1[1]+" ");
             Files.write(path, content.getBytes(charset));
               content = content.replace("."+x11+"", "."+l1[1]+" ");
             Files.write(path, content.getBytes(charset));
               
           }
          
 
          
    }
        
        
        
             for(int i=0;i<names.size();i++){
            
        String la = (names.get(i)).toString();
        String[] l1 = la.split("\\.");
      //  System.out.println(la);
                
          for(int j=0;j<l1.length;j++){
        //       System.out.println(l1[j]);  
          }
          
           int x11 = Integer.parseInt(l1[0]);
           
       //     System.out.println(x11);
           if(x11<10){
             content = content.replace("."+x11+" ", "."+l1[1]+" ");
             Files.write(path, content.getBytes(charset));
          content = content.replace(l1[0]+"",l1[1]+"");
             Files.write(path, content.getBytes(charset));
               
           }
          
 
          
    }   

    }
     //*******************************************************************************************************************    
     private static void torulefilestart(double sup, double conf,int rows) throws IOException {
        
        File f = new File("Rule.txt");
        if(f.exists()){
            
            f.delete();
        }
        boolean createNewFile = f.createNewFile();
        BufferedWriter output = new BufferedWriter(new FileWriter(f , true));
       
   output.write("Summary:");
        String a = "Total rows in the original set:"+rows;
        output.write(a);
        output.newLine();
        a = "Total rules discovered: " + rules.size();
        output.write(a); output.newLine();
        a = "The selected measures: Support="+sup+" Confidence="+conf;
        output.write(a);output.newLine();
        a= "---------------------------------------------------------";
        output.write(a); output.newLine();
     
        
        output.close();
       
       
             
 }
    //*******************************************************************************************************************   
   private static void torulefile(double sup, double conf,int rows) throws IOException {
    
        File f = new File("temp.txt");
        boolean createNewFile = f.createNewFile();
        BufferedWriter output = new BufferedWriter(new FileWriter(f));
        int counter=1;
      
        

        
        output.write("Rules:");
        output.newLine();output.newLine();
         for(int i=0;i<rules.size();i++){
                output.newLine();
                output.write("Rule#");
                output.newLine();
                output.write(rules.get(i));
                output.newLine();
         }
         
                output.close();
                 
                System.out.println("The result is in the file Rules.\n" +"*** Algorithm Finished ***");
                
                
    }
    //*******************************************************************************************************************   
   private static void pruneit(Set<String> s, String sap,File f2,int rows,double conf) throws FileNotFoundException {
      String replace1="";
      List list = new ArrayList(s);
        // System.out.println(s);
        //   System.out.println(list.get(list.size()-1));
      Object get = list.get(list.size()-1);
      String Thelastval = get.toString();
        //   System.out.println(sap);
      String[] array = new String[s.size()];
      String toString = s.toString();
      String[] split = toString.split("\\[");
      String[] replace = new String[split.length-1];
      String[] num = new String[split.length-1];
         
         for(int i=0;i<split.length-1;i++){
              int kount=0,supc=0;
              replace[i] = split[i].replace("],", "");
              //    System.out.println(replace[i]);
              num[i] = replace[i].replaceAll("[^\\d,]", "");
          
             
              Scanner s2=new Scanner(f2);
              s2.nextLine(); 
              String[] split1 = num[i].split(",");
              if(split1.length>0){
              //     System.out.println(num[i]);
              while(s2.hasNextLine()){
                 kount=0;
                 String line = s2.nextLine(); 
                     for(int j=0;j<split1.length;j++){
                         if(j==((split1.length)-1)){
                             if(line.contains(split1[j])){
                               kount++;
                               //System.out.println("kount = "+kount);
                               }
                             else{}
                         }
                         else if(line.contains(split1[j]+" ")){
                               kount++;
                               //System.out.println("kount = "+kount);
                         }
                         if(split1.length == kount){
                             
                            supc++;
                            // 
                         }
                     }
                     
                 }  
             
          //  System.out.println("count = "+supc);
            double supc1 = (double) supc/rows;
            double sup11 = Double.parseDouble(sap);
            double confi = ((double) (supc+sup11)/((double) (rows))); 
       //     System.out.println("confidence ="+confi);
           // System.out.println(replace[i]);
            if(confi>conf){
                
                 String[] split2 = replace[i].split("\\,");
                 
                 replace1=Thelastval;
                  for(int k=0;k<split2.length;k++){
                //   System.out.println(split2[k]+"the string "+Thelastval);
                   replace1 = replace1.replaceAll(split2[k].trim(), " ");
                   replace1 = replace1.replaceAll("\\ , ", " ");
                   replace1 = replace1.replaceAll(" ", "");
                   replace1 = replace1.replaceAll("\\,]", "\\]");
                //   System.out.println("the replaced string "+replace1);
                  }
                //    System.out.println( "length= "+replace[i].length());
                  if(replace[i].length()>0 && replace[i].length()>1){
                      replace[i] = replace[i].replaceAll("^\\ ", "");
                      rules.add(replace[i]+"->"+replace1);
                  }
            }
           }
         }// the big for ends
        }//method ends
     //*******************************************************************************************************************   
   private static LinkedHashSet subsetG(String[] set) {
     
       LinkedHashSet p = new LinkedHashSet();
       int e = set.length;
       int pe = (int) Math.pow(2,e);
       String s="";
       //---------------------------------------------
       for (int i = 0; i < pe-1; i++) {
           String binary = iToB(i, e);
           LinkedHashSet innerSet = new LinkedHashSet();
           for (int j = 0; j < binary.length(); j++) {
               if (binary.charAt(j) == '1')
                   innerSet.add(set[j]);//s=set[j];
               
           }
           
         
           p.add(innerSet);
             System.out.println(p);
       }
       return p;
   }
     //*******************************************************************************************************************   
   private static String iToB(int binary, int digits) {
     
       String t = Integer.toBinaryString(binary);
        //System.out.println(t);
       int s = t.length();
        //System.out.println(s);
       String r = t;
       for (int i = s; i < digits; i++) {
           r = "0" + r;
       }
      //System.out.println(r);
       return r;
   } 
     //*******************************************************************************************************************   
    private static String copyFile(String from) throws IOException {
      
        String t= "New"+from;
        File oo = new File(t);
        if(oo.exists()){
            oo.delete();
        }  
        Path source = Paths.get(from);
        Path nwdir = Paths.get(t);
        Files.copy(source, nwdir);
        return t;

    }
    //*******************************************************************************************************************  
     static ArrayList<String> names = new ArrayList<String>();
    static ArrayList<String> names1 = new ArrayList<String>();
    private static void numerify(String file) throws FileNotFoundException, IOException {
        File f1 = new File(file);
        int cols=0;
        //for headings to A B C D E
       Scanner s1=new Scanner(f1);
      if(s1.hasNextLine()){
            String head = s1.nextLine();
            String[] split = head.split(" ");
            cols = split.length;
      Path path = Paths.get(file);
      Charset charset = StandardCharsets.UTF_8;
      String content = new String(Files.readAllBytes(path), charset);
      char a='A';
        for(int i=0;i<cols;i++){
             names.add(a+"."+split[i]);
            if(i==(cols-1)){
            String chara = Character.toString(a);
           
          content = content.replaceAll(split[i], chara);
          Files.write(path, content.getBytes(charset));
         // System.out.println(split[i] +" becomes "+a);
          a++;
                
            }
           String chara = Character.toString(a);
          content = content.replaceAll(split[i]+" ", chara+" ");
          Files.write(path, content.getBytes(charset));
         // System.out.println(split[i] +" becomes "+a);
          a++;
          
      }
         
      }
      
      
      int rows=0;
       Scanner s2=new Scanner(f1);
      while(s2.hasNextLine()){
            s2.nextLine();
           rows++;
      }
      
      //----------data to number---------------------------------------------
      
      int a = 1;
      ArrayList<String> Ilist = new ArrayList<String>();
      int m=0;
      Path path = Paths.get(file);
      Charset charset = StandardCharsets.UTF_8;
      String content = new String(Files.readAllBytes(path), charset);
      for(int i=0;i<cols;i++){
                Scanner s3=new Scanner(f1);
                String head1 = s3.nextLine();
                while(s3.hasNextLine()){
                         String head = s3.nextLine();
                         String[] split = head.split(" ");
                         cols = split.length;
                         Ilist.add(split[i]);
              }
                  if(!head1.equals(null)){
             Set<String> s = new LinkedHashSet<String>(Ilist);
             String[] Uniqueitems = s.toArray(new String[s.size()]);//list to array
             for(int j=0;j<Uniqueitems.length;j++){
                   String number = Integer.toString(a);
              //     System.out.println(Uniqueitems[j]);
              //     System.out.println(number);
                   names1.add(number+"."+Uniqueitems[j]);
                   content = content.replaceAll(Uniqueitems[j], number);
                   Files.write(path, content.getBytes(charset));
                   a++;
              }
      }
             Ilist.clear();
    }//for ends
      
    }//method ends
     //*******************************************************************************************************************   
    private static int row(File f1) throws FileNotFoundException {
       Scanner s1 = new Scanner(f1);
        int i =0;
             if (f1.exists()){
                     while(s1.hasNextLine()){
                         s1.nextLine();
                         i++;  
                     }
            }
        return i;      
    }
     //*******************************************************************************************************************   
    private static int col(File f1) throws FileNotFoundException {
        Scanner s1 = new Scanner(f1);
        int i =0;
             if (f1.exists()){
                     if(s1.hasNextLine()){
                       
                           String acl = s1.nextLine();
                           String[] parts =  acl.split(" ");
                           i= parts.length;
                      }
            }
        return i;      
    }
     //*******************************************************************************************************************   
    private static String Createitem(File f1,int row,int col) throws FileNotFoundException {
    //  int[][] values = new int[row][col];
      String line = "";
       if (f1.exists()){
           for(int i=0;i<col;i++){
               Scanner s1 = new Scanner(f1);  
                     while(s1.hasNextLine()){
                           String a= s1.nextLine(); 
                           String[] parts =  a.split(" ");
                           line += parts[i]+",";
                          //System.out.println(line);
                     }
            }
       }
       return line;
    }
     //*******************************************************************************************************************   
    private static String itemset1(String line, int rows, int cols) {
    String[] toItems = line.split(",");
    String items = "";
        for(int j=0;j<cols*rows;j+=rows){
                //System.out.println("value of j"+j);   
                for(int i=0;i<rows;i++){
               // System.out.println(toItems[j]+" "+toItems[i+j]);
                 items += toItems[j]+"."+toItems[i+j]+":";//
                }

        }
     return items;
   }
     //*******************************************************************************************************************   
    private static String[] itemcount(String items,int rows, int cols,double sup) {
   
      //  System.out.println(items);
        int count=0,j=0,k=0,colc=0;
        String[] split = items.split(":");
        Set<String> s = new LinkedHashSet<String>(Arrays.asList(split));
       // System.out.println(s);
// TO remove A,A B,B C,C

        while(k <(cols)){
        String a = split[colc];
        s.remove(a);
        colc += rows;
        k++;
        }
        String[] Uniqueitems = s.toArray(new String[s.size()]);//list to array
      
           ArrayList<String> Ilist = new ArrayList<String>();
         //  ArrayList<String> Ilist2 = new ArrayList<String>();
        for(int i=0;i<Uniqueitems.length;i++){
                count=0;
                Pattern p = Pattern.compile(Uniqueitems[i]);
                Matcher m = p.matcher(items);
                    while (m.find()){
                    count +=1;
                    }
                double support = ((double) count/(double)(rows-1));
               // System.out.println(Uniqueitems[i]+" "+count+" "+support);
             
//To get min sup Frequent 1 itemsets 
               if(support > sup){
                    
                    Ilist.add(Uniqueitems[i]);
                  //  Ilist2.add(""+count);
                }
        }
// list to array convert
         // System.out.println("Frequnt 1 Itemset count: "+Ilist.size());
          String[] ItemSet1 = new String[Ilist.size()];  
          int l=0;
            for (int i = 0; i < Ilist.size(); i++){
                ItemSet1[i] = Ilist.get(i);
              //  ItemSet1[i][l+1] =Ilist2.get(i);
            }

        return ItemSet1;
  
   }
     //*******************************************************************************************************************   
    static ArrayList<String> su2 = new ArrayList<String>();
    private static String[] itemset2(String[] Itemset, int rows, int cols,File f1,double sup) throws FileNotFoundException {
        
       String Itemset1="";
        for(int i=0;i<Itemset.length;i++){
            
                 Itemset1 += Itemset[i]+",";
            
       }
        
    //   System.out.println(Itemset1);
       
        ArrayList<String> list = new ArrayList<String>();
      // System.out.println();
       for(int i=0;i<Itemset.length;i++){
              for(int j=i+1;j<Itemset.length;j++){
                   
                   char a  = Itemset[i].charAt(0);
                   char b  = Itemset[j].charAt(0);
                 //  System.out.println(a+" "+b);
                   
                  if(a != b){
                    //System.out.println(Itemset[i]+","+Itemset[j]);
                    list.add(Itemset[i]+","+Itemset[j]);
                     }
               }
         }
    
       
       //System.out.println(list);
        String[] item2 = list.toArray(new String[list.size()]);//list to array
       String[] mat = new String[list.size()];
        int count;
        ArrayList<String> Itemse2 = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            count=0;
           //System.out.println(item2[i]);
           String[] split = item2[i].split(",|\\.");
          Scanner s2=new Scanner(f1);
            while(s2.hasNextLine()){
            String line = s2.nextLine();
                if(line.contains(split[1]) && line.contains(split[3])){
                       count +=1;
                      mat[i] = item2[i];
                }
            }//while ends
            
              double support = ((double) count/(double)(rows-1));
          //  System.out.println(mat[i]+"sup "+support);
            if(support > sup){
                    
                    Itemse2.add(mat[i]);
                    su2.add(support+"");
            }
                
           
            
        }// for ends
        
            String[] ItemSet2 = new String[Itemse2.size()];  
            for (int i = 0; i < Itemse2.size(); i++){
                ItemSet2[i] = Itemse2.get(i);
            }
            
              String[] num= new String[ItemSet2.length];
           for(int k=0;k<ItemSet2.length;k++){
                    num[k] = ItemSet2[k].replaceAll("[^\\d,]", "");
                      
           }
           
            
            
            return  ItemSet2;
       }//method ends wala  {
    //*******************************************************************************************************************   
    static ArrayList<String> su = new ArrayList<String>();
    private static String[] nitemset(String[] Itemset2, int rows, int cols, File f1, double sup) throws FileNotFoundException {
         
        
        
     ArrayList<String> list = new ArrayList<String>(); 
        int count=0;
        for(int i=0;i<Itemset2.length;i++){
              for(int j=i+1;j<Itemset2.length;j++){
                   
                  list.add(Itemset2[i]+","+Itemset2[j]);
               //   System.out.println(Itemset2[i]+","+Itemset2[j]);
                  count++;  
               }
         }

     
     
        //  Set<String> s = new LinkedHashSet<String>(list);
        String[] items = list.toArray(new String[list.size()]);//list to array
      
     // System.out.println("------------------------------------");
      ArrayList<String> list3 = new ArrayList<String>(); 
      ArrayList<String> list2 = new ArrayList<String>(); 
   
      for(int i=0;i<items.length;i++){
         
           String[] split = items[i].split(",");
           
                   for(int j=0;j<1;j++){
                   int c=0;
                   list2.clear();
                    Pattern p = Pattern.compile(split[j]);
                    Matcher m = p.matcher(items[i]);
                        while (m.find()){
                        c +=1;
                        }
                        
                    if(c==2){
              //      System.out.println(split[j]+".... "+items[i]);   
                   // System.out.println(c); 
                    for(int k =0;k<split.length;k++){
                        list2.add(split[k]);
                        
                    }
                    
                    }
                  //  System.out.println(list2);
                    Set<String> s = new LinkedHashSet<String>(list2);
                  
                   // System.out.println(s);
                    if(!(s.toString()).equals("[]")){
                        
                         String replaceAll = s.toString().replaceAll("\\[|\\]", "");
                         
                        list3.add( replaceAll);
                    }
                   }
                }

           String[] split = list3.toArray(new String[list3.size()]);//list to array
           
           String[] num= new String[split.length];
           for(int k=0;k<split.length;k++){
                    num[k] = split[k].replaceAll("[^\\d,]", "");
                      
           }
           
 
           ArrayList<String> list4 = new ArrayList<String>(); 
           int kount=0;
           for(int k=0;k<split.length;k++){
             //      System.out.println( num[k] );
                    String[] split1 = num[k].split(",");
                     Scanner s2=new Scanner(f1);
                     int supc=0;
                    while(s2.hasNextLine()){
                                String line = s2.nextLine();
                                
                                for(int m=0;m<split1.length;m++){
                                    
                                 if(line.contains(split1[m]+" ")){
                                 //  System.out.println( "if"+line+"contains "+split1[m]+"" );
                                   kount++;  
                                if(kount==3){
                                      kount=0;
                                     supc++;
                                     
                                   } 
                                 } 
                                }
                                
                    }
                     double support = ((double) supc/(double)(rows-1));
                                 if(support > sup){
                                     su.add(support+"");
                                      list4.add(split[k]);
                                 }
           } 
          //  System.out.println( "xxx"+list4 );
           
           String[] split3 = list4.toArray(new String[list4.size()]);//list to array
           
             String[] num1= new String[Itemset2.length];
             ArrayList<String> set = new ArrayList<String>();
             ArrayList<String> set2 = new ArrayList<String>();
             for(int k=0;k<Itemset2.length;k++){
                    num1[k] = Itemset2[k].replaceAll("[^\\d,]", "");
                   //  System.out.println( Itemset2[k]);
                      set.add(num1[k]);
           }
         //    System.out.println(  set);
             String[] num2= new String[split.length];
               for(int k=0;k<split.length;k++){
                   
                   num2[k] = split[k].replaceAll("[^\\d,]", "");
                  //  System.out.println( num[k] );
                    String[] split12 = num2[k].split(",");
                     if(set.contains( split12[0]+","+split12[1] )){
                            if(set.contains(split12[0]+","+split12[2])){
                                   if(set.contains(   (split12[1]+","+split12[2]))){
                                    //  System.out.println("ha");
                                        set2.add(split[k]);
                            }
                         
                     }
                     }
                 }
        split3 = set2.toArray(new String[set2.size()]);//list to array
        return split3;
         }//method ends
    //*************************************************4444444444444******************************************************************   
    static ArrayList<String> suppp = new ArrayList<String>();
    private static String[] nitemset4(String[] Itemset2, int rows, int cols, File f1, double sup) throws FileNotFoundException {
         
       // System.out.println("here");
        
     ArrayList<String> list = new ArrayList<String>(); 
        int count=0;
        for(int i=0;i<Itemset2.length;i++){
              for(int j=i+1;j<Itemset2.length;j++){
                   
                  list.add(Itemset2[i]+","+Itemset2[j]);
              //   System.out.println(Itemset2[i]+","+Itemset2[j]);
                  count++;  
               }
         }

     
     
        //  Set<String> s = new LinkedHashSet<String>(list);
        String[] items = list.toArray(new String[list.size()]);//list to array
      
    //  System.out.println("------------------------------------");
      ArrayList<String> list3 = new ArrayList<String>(); 
      ArrayList<String> list2 = new ArrayList<String>(); 
   
      for(int i=0;i<items.length;i++){
         
           String[] split = items[i].split(",");
           
                   for(int j=0;j<1;j++){
                   int c=0;
                   list2.clear();
                    Pattern p = Pattern.compile(split[j]);
                    Pattern p1 = Pattern.compile(split[j+1]);
                    Matcher m = p.matcher(items[i]);
                        while (m.find()){
                        c +=1;
                        }
                        Matcher m1 = p1.matcher(items[i]);
                        while (m1.find()){
                        c +=1;
                        }
                        
                        
                    if(c==4){
                 //   System.out.println(split[j]+".... "+items[i]);   
               //     System.out.println(c); 
                    for(int k =0;k<split.length;k++){
                        list2.add(split[k]);
                        
                    }
                    
                    }
                  //  System.out.println(list2);
                    Set<String> s = new LinkedHashSet<String>(list2);
                  
                   // System.out.println(s);
                    if(!(s.toString()).equals("[]")){
                        
                         String replaceAll = s.toString().replaceAll("\\[|\\]", "");
                         
                        list3.add( replaceAll);
                    }
                   }
                }
// System.out.println(list3);
           String[] split = list3.toArray(new String[list3.size()]);//list to array
           
           String[] num= new String[split.length];
           for(int k=0;k<split.length;k++){
                    num[k] = split[k].replaceAll("[^\\d,]", "");
                      
           }
           
 
           ArrayList<String> list4 = new ArrayList<String>(); 
           int kount=0,supc=0;
           for(int k=0;k<split.length;k++){
             //      System.out.println( num[k] );
                    String[] split1 = num[k].split(",");
                     Scanner s2=new Scanner(f1);
                     supc=0;
                     
                    while(s2.hasNextLine()){
                       
                                String line = s2.nextLine();
                                
                                for(int m=0;m<split1.length;m++){
                                  //   System.out.println( split1[m]+"" );
                                 if(line.contains(split1[m]+" ")){
                                     
                                   //System.out.println( "if"+line+"contains "+split1[m]+"" );
                                   kount++;
                                    
                                  }
                                if(kount==4){
                                     
                                     kount = 0;
                                     supc++;
                                 // System.out.println( supc);
                                      
                                   }
                                  
                                 } 
                                
                    }
                       double support = ((double) supc/(double)(rows-1));
                                  // System.out.println(support);
                                 
                                   if(support > sup){
                                       suppp.add(support+"");
                                      list4.add(split[k]);
                                 }
           } 
           
           
          //  System.out.println( "xxx"+list4 );
           
           //  Set<String> s1 = new LinkedHashSet<String>(list4);
             list4 = new ArrayList<String>(new HashSet<String>(list4));
            String[] split3 = list4.toArray(new String[list4.size()]);//list to array
        return  split3;
         }//method ends
    }//class ends