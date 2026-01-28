package microarch.delivery.core.domain.model.delivery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;



class LocationTest {
    @Test
    @DisplayName("Объект Location был наследован от объекта 'ValueObject'")
    void derivedAggregate() {
        assertThat(Location.class.getSuperclass().getSimpleName()).isEqualTo("ValueObject");
    }

    @Test
    @DisplayName("Должен вернуться результат с корректно созданным объектом Location")
    void shouldBeCreatedLocationWithCorrectParams() {
        var result = Location.create(10, 10);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getValue()).isNotNull();
        assertThat(result.getValue().getX()).isEqualTo(10);
        assertThat(result.getValue().getY()).isEqualTo(10);
    }

    @Test
    @DisplayName("Должен вернуться результат с ошибкой при не корректном первом параметре создания объекта Location")
    void shouldReturnErrorWhenFirstParamsNotCorrect() {
        var result = Location.create(11, 5);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getError().getMessage())
                .isEqualTo("Value 11 for x is out of range. Min value is 1, max value is 10.");
    }

    @Test
    @DisplayName("Должен вернуться результат с ошибкой при не корректном втором параметре создания объекта Location")
    void shouldReturnErrorWhenTwoParamsNotCorrect() {
        var result = Location.create(5, 11);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getError().getMessage())
                .isEqualTo("Value 11 for y is out of range. Min value is 1, max value is 10.");
    }

    @Test
    @DisplayName("Два объекта Location с одинаковыми координатами должны быть эквивалентны")
    void shouldBeEqualThenAllParametersIsEquals() {
        var locationOne = Location.create(5,5).getValue();
        var locationTwo = Location.create(5,5).getValue();
        var result = locationOne.equals(locationTwo);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Два объекта Location с разными координатами должны быть не эквивалентны")
    void shouldBeEqualThenAllParametersIsNotEquals() {
        var locationOne = Location.create(1,5).getValue();
        var locationTwo = Location.create(5,5).getValue();
        var result = locationOne.equals(locationTwo);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Расчет дистанции между двумя Location")
    void shouldReturnCorrectDistanceBetweenTwoLocations() {
        var locationOne = Location.create(1,1).getValue();
        var locationTwo = Location.create(3,4).getValue();

        var distance = locationOne.distanceTo(locationTwo);
        assertThat(distance.getValue().intValue()).isEqualTo(5);
    }

    @Test
    @DisplayName("Расчет дистанции между двумя Location должен вернуть ошибку, когда второй Location null")
    void shouldReturnErrorInCalculateDistanceWhenSecondLocationIsNull() {
        var locationOne = Location.create(1, 1).getValue();
        var result = locationOne.distanceTo(null);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.getError().getCode()).isEqualTo("target.is.not.null");
        assertThat(result.getError().getMessage()).isEqualTo("Target not be null");
    }
}