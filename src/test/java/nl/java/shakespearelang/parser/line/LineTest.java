package nl.java.shakespearelang.parser.line;

import nl.java.shakespearelang.CharacterInPlay;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineTest {

    @Test
    public void create_assignment_with_the_difference_between() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "difference between");
        assertThat(line.getWords()).containsExactly("THEDIFFERENCEBETWEEN");
        assertThat(line.getSubject()).isEqualTo(new CharacterInPlay("romeo"));
        assertThat(line.getLine()).isEqualTo("difference between");
    }

    @Test
    public void create_assignment_with_sum_of() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "sum of");
        assertThat(line.getWords()).containsExactly("THESUMOF");
    }

    @Test
    public void create_assignment_with_product_of() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "product of");
        assertThat(line.getWords()).containsExactly("THEPRODUCTOF");
    }

    @Test
    public void create_assignment_with_square_of() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "square of");
        assertThat(line.getWords()).containsExactly("THESQUAREOF");
    }

    @Test
    public void create_assignment_with_quotient_of() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "quotient between");
        assertThat(line.getWords()).containsExactly("THEQUOTIENTBETWEEN");
    }

    @Test
    public void create_assignment_with_cube_of() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "cube of");
        assertThat(line.getWords()).containsExactly("THECUBEOF");
    }

    @Test
    public void create_assignment_with_twice() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "twice");
        assertThat(line.getWords()).containsExactly("TWICE");
    }

    @Test
    public void create_assignment_with_summers_day() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "summer's day");
        assertThat(line.getWords()).containsExactly("summer'sday");
    }

    @Test
    public void create_assignment_with_thyself() {
        Assignment line = new Assignment(new CharacterInPlay("romeo"), "thyself");
        assertThat(line.getWords()).containsExactly("yourself");
    }
}