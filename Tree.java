public class Tree {
    private double weight;
    private char character;
    private Tree left_Child;
    private Tree right_Child;
    private Boolean has_character;

    // The roots character should always be null!!!!
    public Tree() {
        weight = 0;
        left_Child = null;
        right_Child = null;
    }

    public Tree(char character, double weight) {

        this.weight = weight;
        this.character = character;
        left_Child = null;
        right_Child = null;
        has_character = true;
    }

    public Tree(double weight, Tree left_Child, Tree right_Child) {
        this.weight = weight;
        this.left_Child = left_Child;
        this.right_Child = right_Child;
        has_character = false;
    }

    public Tree(Character c, double weight, Tree left_Child, Tree right_Child) {
        this.character = c;
        this.weight = left_Child.get_Weight() + right_Child.get_Weight();
        this.left_Child = left_Child;
        this.right_Child = right_Child;
        has_character = false;
    }

    public void set_character(Character c) {
        this.character = c;
    }

    public void set_has_char(Boolean b) {
        has_character = b;
    }

    public boolean get_has_char() {
        return has_character;
    }

    public void set_left_child(Tree new_child) {
        left_Child = new_child;
    }

    public void set_right_child(Tree new_child) {
        right_Child = new_child;
    }

    public Tree get_right_child() {
        return right_Child;
    }

    public Tree get_left_child() {
        return left_Child;
    }

    public double get_Weight() {
        return weight;
    }

    public char get_Character() {
        return character;
    }
}
