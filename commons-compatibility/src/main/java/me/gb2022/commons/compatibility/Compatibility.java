package me.gb2022.commons.compatibility;

import java.util.HashSet;
import java.util.Set;

public final class Compatibility {
    private final Set<CompatibilityAssertion[]> failAny = new HashSet<>();
    private final Set<CompatibilityAssertion[]> failAll = new HashSet<>();
    private final Set<CompatibilityAssertion> test = new HashSet<>();

    public static CompatibilityAssertion type(CompatibilityAssertion.ClassAssertion assertion) {
        return assertion;
    }

    public static CompatibilityAssertion method(CompatibilityAssertion.MethodAssertion assertion) {
        return assertion;
    }

    public static CompatibilityAssertion value(CompatibilityAssertion.ValueAssertion assertion) {
        return assertion;
    }

    public static CompatibilityAssertion reverse(CompatibilityAssertion assertion) {
        return () -> {
            try {
                assertion.run();
                throw new APIIncompatibleException("passed: " + assertion);
            } catch (Throwable ignored) {
            }
        };
    }

    public static Set<Throwable> check(CompatibilityAssertion[] assertions) {
        var result = new HashSet<Throwable>();

        for (CompatibilityAssertion assertion : assertions) {
            try {
                assertion.run();
            } catch (Throwable e) {
                result.add(e);
            }
        }

        return result;
    }

    public static boolean checkFailAny(CompatibilityAssertion[] assertions, Set<Throwable> report) {
        var result = check(assertions);

        report.addAll(result);

        return result.isEmpty();
    }

    public static boolean checkFailAll(CompatibilityAssertion[] assertions, Set<Throwable> report) {
        var result = check(assertions);

        report.addAll(result);

        return result.size() != assertions.length;
    }

    public void test(CompatibilityAssertion assertion) {
        this.test.add(assertion);
    }

    public void failAny(CompatibilityAssertion... assertions) {
        this.failAny.add(assertions);
    }

    public void failAll(CompatibilityAssertion... assertions) {
        this.failAll.add(assertions);
    }

    public boolean check(Set<Throwable> report) {
        var passed = true;

        for (var assertions : this.failAny) {
            if (!checkFailAny(assertions, report)) {
                passed = false;
            }
        }

        for (var assertions : this.failAll) {
            if (!checkFailAll(assertions, report)) {
                passed = false;
            }
        }

        var a = check(this.test.toArray(new CompatibilityAssertion[0]));

        if (!a.isEmpty()) {
            passed = false;
        }

        report.addAll(a);

        return passed;
    }


    public void run() throws APIIncompatibleException {
        try {
            for (CompatibilityAssertion assertion : this.test) {
                assertion.run();
            }
        } catch (APIIncompatibleException e) {
            throw e;
        } catch (Throwable e) {
            throw new APIIncompatibleException("EX_OTHER: " + e.getMessage());
        }
    }
}
