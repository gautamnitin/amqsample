# Test Configuration Fix

## Issue
The application was failing during test execution with the following error:

```
The bean 'tradeQueue', defined in class path resource [com/jmsmq/amqsample/config/QueueConfig.class], could not be registered. A bean with that name has already been defined in class path resource [com/jmsmq/amqsample/TestConfig.class] and overriding is disabled.
```

## Root Cause
The issue occurred because:

1. `QueueConfig.java` defines a bean named `tradeQueue` that creates a real ActiveMQQueue
2. `TestConfig.java` also defines a bean named `tradeQueue` that creates a mock Queue for testing
3. Spring Boot 2.1+ disables bean overriding by default, causing the conflict

## Solution
The solution was to enable bean definition overriding for tests by adding the following property to `src/test/resources/application.properties`:

```properties
spring.main.allow-bean-definition-overriding=true
```

This allows the test configuration to override the main application beans, which is appropriate for testing scenarios where we want to use mock objects instead of real implementations.

## Alternative Solutions Considered

1. **Rename one of the beans**: This would require changing the bean name in either the main code or test code, potentially breaking existing references.

2. **Use different profiles**: We could have used Spring profiles to conditionally define beans, but this would add complexity.

3. **Remove the @Primary annotation**: The test bean already has @Primary, but this alone doesn't resolve the issue when overriding is disabled.

The chosen solution is the simplest and most maintainable approach, as it:
- Doesn't require changing any Java code
- Follows Spring Boot's recommended approach for testing
- Only affects the test environment, not production code