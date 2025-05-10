package org.example

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

object DbInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val propertyValues =
            TestPropertyValues.of(
                "spring.datasource.url=" + "jdbc:postgresql://localhost:5432/test",
                "spring.datasource.username=" + "test",
                "spring.datasource.password=" + "test",
            )
        propertyValues.applyTo(applicationContext.environment)
    }
}
