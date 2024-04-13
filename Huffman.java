/*
* Author: 
* Implements Huffman's binary encoding algorithm
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Huffman {
	// TODO: Write docstrings or something like that
	public static void main(String[] args) throws IOException {
		String my_poem = "a neat east sea nest / ten nets net a tan sea nase / an east ant at sea";
		// the "Common Nase" are a kind of fish I swear
		// Compresses to 192 bits (see decode test)
		System.out.println(encode(my_poem));
		// TODO: breaks when there is only one character!!!!
		// TODO: remove unused stuff.

		System.out.println(decode(
				"000110011100110011110010111100110111110001100111101111001101001110111100011001111101011011001111100100011100010001101111100011000010111110110100100100011110010111100100100110010011001101111100",
				"111: e\n110: t\n1011: s\n1010: /\n100: n\n01:  \n00: a"));
	}

	public static String decode(String bit_String, String dict_String) {
		String output_String = "";
		// TODO: put 29-50 in a seperate function!
		HashMap<Integer, ArrayList<String>> dictionary = new HashMap<Integer, ArrayList<String>>();
		String[] temp_definition_array = dict_String.split("\n");
		System.out.println(temp_definition_array.length);
		Integer smallest_def = temp_definition_array[0].indexOf(":");

		for (int i = 0; i < temp_definition_array.length; i++) {

			int length_of_def = temp_definition_array[i].indexOf(":");

			if (dictionary.containsKey(length_of_def)) {
				ArrayList<String> a = dictionary.get(length_of_def);
				a.add(temp_definition_array[i]);
				dictionary.put(length_of_def, a);
			} else {
				ArrayList<String> temp = new ArrayList<>();
				temp.add(temp_definition_array[i]);
				dictionary.put(length_of_def, temp);
			}
			if (length_of_def < smallest_def) {
				smallest_def = length_of_def;
			}
		}
		Object[] keySet = dictionary.keySet().toArray();
		System.out.println(dictionary.get(4));
		System.out.println(keySet.length - 1);
		int i1 = 0;
		int current_Length = smallest_def;
		while (i1 + current_Length <= bit_String.length()) {
			// TODO: It manages to match the first one, but then falls into an infinite
			// loop.
			boolean match_found = false;
			while (!match_found && current_Length <= (Integer) keySet[keySet.length - 1]) {
				String bit_subString = bit_String.substring(i1, i1 + current_Length);
				// System.out.println("Current length = " + current_Length);
				for (String s : dictionary.get(current_Length)) {
					String[] s_parts = s.split(":");
					if (bit_subString.equals(s_parts[0])) {
						// System.out.println("character found!");
						match_found = true;
						i1 += current_Length;
						current_Length = smallest_def;

						output_String += s_parts[1].replaceFirst(" ", ""); // adds the character (last character in the
																			// string)to the output string
						break;
					}
				}
				if (!match_found) {
					current_Length++;
				}

				// change the size of the bit_substring!
			}
			if (match_found) {

			} else {
				return "Invalid String";
			}
		}

		return output_String;
	}

	public static String encode(String message) {
		// Uses Huffman's algorithm to encode message into binary
		// Returns the binary encoding of the input String
		// Also printsa dictionary.

		HashMap<Character, Double> weight_map = generate_Char_Weight_Map(message);
		Comparator<Tree> treeComparator = new TreeCompare();
		PriorityQueue<Tree> tree_Queue = new PriorityQueue<Tree>(message.length(), treeComparator);
		for (Character c : weight_map.keySet()) {
			tree_Queue.add(new Tree(c, weight_map.get(c)));
		}

		while (tree_Queue.size() > 1) {
			Tree first_Tree = tree_Queue.poll();
			Tree second_Tree = tree_Queue.poll();
			double weight_sum = first_Tree.get_Weight() + second_Tree.get_Weight();
			tree_Queue.add(new Tree(weight_sum, first_Tree, second_Tree));
		}
		Tree final_Tree = tree_Queue.poll();
		return generate_Text_For_Tree("", final_Tree);
	}

	public static HashMap<Character, Double> generate_Char_Weight_Map(String s) {
		int str_length = s.length();
		HashMap<Character, Double> map = new HashMap<>();
		for (Character c : s.toCharArray()) {
			if (map.containsKey(c)) {
				map.put(c, map.get(c) + 1.0);
			} else {
				map.put(c, 1.0);
			}
		}
		for (Character k : map.keySet()) {
			map.put(k, map.get(k) / str_length);
		}
		return map;
	}

	public static String generate_Text_For_Tree(String output_String, Tree tree) {
		if (tree.get_right_child() == null && tree.get_left_child() == null) {
			return output_String + ": " + tree.get_Character() + "\n";

		}
		return generate_Text_For_Tree(output_String + "1", tree.get_right_child()) + generate_Text_For_Tree(
				output_String + "0", tree.get_left_child());
	}

}

class TreeCompare implements Comparator<Tree> {
	public int compare(Tree a, Tree b) {
		// does tree a have smaller weight than tree b: return 1 if yes, 0 if no.
		if (a.get_Weight() >= b.get_Weight()) {
			return 1;
		}
		return -1;
	}
}