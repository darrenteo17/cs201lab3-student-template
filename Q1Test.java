public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
      /**
       * Plan:
       * 1. If the number of elements of the 3 traversals is not the same, it is definitely invalid.
       * 2. Assume traversal1 is preorder traversal
       * 3. Build the binary search tree using that traversal
       * 4. Generate the preorder of that tree first, if it doesnt the assumed string then its wrong, so go to step 6
       * 5. If preorder matches, generate the inorder and postorder, and check if the other 2 strings match also (if match, label them)
       * 6. If it does not match with the input strings, repeat from step 2 using the other input traversal string.
       * 7. If all 3 input traversal strings dont work, means they are invalid.
       */
     public static String verify(String traversal1, String traversal2, String traversal3){
          // tokenize the strings to get the numbers only, in an array
          String[] t1 = traversal1.split("-");
          String[] t2 = traversal2.split("-");
          String[] t3 = traversal3.split("-");
          
          // first check is if the lengths(number of elements) are not all the same, it is invalid.
          if(!(t1.length == t2.length && t1.length == t3.length)) {
               return "Invalid traversals";
          }

          // put them in arrays so we can loop them over by index
          String[] inputs = {traversal1, traversal2, traversal3};
          String[][] tokens = {t1, t2, t3};

          // try each string as the preorder
          for(int i = 0; i < 3; i++) {
               // build the bst
               Node root = null;
               for(String s : tokens[i]) {
                    root = insert(root, Integer.parseInt(s));
               }

               // generate the preorder traversal first then check the guess
               StringBuilder preSb = new StringBuilder();
               preorder(root, preSb);
               if(!inputs[i].equals(preSb.toString())) {
                    continue;
               }

               // if guess is a valid preorder, generate the other 2 traversals
               StringBuilder inSb = new StringBuilder();
               StringBuilder postSb = new StringBuilder();
               inorder(root, inSb);
               postorder(root, postSb);
               String in = inSb.toString();
               String post = postSb.toString();

               // a and b are the indexes of the two other strings
               int a = (i + 1) % 3;
               int b = (i + 2) % 3;
               String[] labels = new String[3];
               labels[i] = "Preorder";

               // the other two must be inorder & postorder, in either order
               if(inputs[a].equals(in) && inputs[b].equals(post)) {
                    labels[a] = "Inorder";
                    labels[b] = "Postorder";
               } else if (inputs[a].equals(post) && inputs[b].equals(in)) {
                    labels[a] = "Postorder";
                    labels[b] = "Inorder";
               } else {
                    continue;
               }

               return "Traversal 1 - " + labels[0] + ", Traversal 2 - " + labels[1] + ", Traversal 3 - " + labels[2];
          }

          // no guess worked
          return "Invalid traversals";
     }

     // to insert the value into the bst and returns the root of the subtree
     public static Node insert(Node root, int value) {
          // if empty spot found, create the new node here
          if(root == null) {
               return new Node(value);
          }

          // smaller values go left, larger go right
          if(value < root.value) {
               root.left = insert(root.left, value);
          } else {
               root.right = insert(root.right, value);
          }

          return root;
     }

     public static void preorder(Node node, StringBuilder sb) {
          if(node == null) {
               return;
          }
          visit(node, sb);
          preorder(node.left, sb);
          preorder(node.right, sb);
     }

     public static void inorder(Node node, StringBuilder sb) {
          if(node == null) {
               return;
          }
          inorder(node.left, sb);
          visit(node, sb);
          inorder(node.right, sb);
     }

     public static void postorder(Node node, StringBuilder sb) {
          if(node == null) {
               return;
          }
          postorder(node.left, sb);
          postorder(node.right, sb);
          visit(node, sb);
     }

     // add the node's value to the string with a '-' infront if it is not the first value
     public static void visit(Node node, StringBuilder sb) {
          if(sb.length() > 0) {
               sb.append("-");
          }
          sb.append(node.value);
     }
}

class Node {
     int value;
     Node left, right;

     Node(int value) {
          this.value = value;
     }
}
