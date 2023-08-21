package com.bakery.shop;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

/**
 * Architecture test
 */
class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.bakery.shop");

        noClasses()
            .that()
            .resideInAnyPackage("com.bakery.shop.service..")
            .or()
            .resideInAnyPackage("com.bakery.shop.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.bakery.shop.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
