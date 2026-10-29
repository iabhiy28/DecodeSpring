import java.util.*;
import java.util.stream.Collectors;

public class Exercise3 {

    public static void main(String[] args) {

        // Task 1: Stream Pipeline
        List<Integer> ages = List.of(12, 25, 18, 40, 15, 30, 22);

        List<Integer> result = ages.stream()
                .filter(x -> x >= 18)
                .map(x -> x * 2)
                .collect(Collectors.toList());

        System.out.println("Adults doubled: " + result);

        // Task 2: Optional handling
        // 1. Found case (id = 101)
        Optional<String> usernameOpt = findUsernameById(101);
        usernameOpt.ifPresent(name -> System.out.println("Found user: " + name.toUpperCase()));

        // 2. Absent case (id = 999) with fallback
        String fallbackUser = findUsernameById(999).orElse("Default_Guest");
        System.out.println("Fallback user: " + fallbackUser);
    }

    public static Optional<String> findUsernameById(int id) {
        if (id == 101) {
            return Optional.of("abhishek");
        }
        return Optional.empty();
    }
}