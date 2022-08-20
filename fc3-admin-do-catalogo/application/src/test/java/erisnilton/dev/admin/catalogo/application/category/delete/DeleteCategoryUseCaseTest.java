package erisnilton.dev.admin.catalogo.application.category.delete;

import erisnilton.dev.admin.catalogo.domain.category.Category;
import erisnilton.dev.admin.catalogo.domain.category.CategoryGateway;
import erisnilton.dev.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        reset(categoryGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doNothing().when(categoryGateway).deleteById(eq(expectedId));
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var expectedId = CategoryID.from("123");

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));


    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {

        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway Error")).when(categoryGateway).deleteById(eq(expectedId));
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));


    }


}
