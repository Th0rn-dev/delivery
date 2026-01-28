package microarch.delivery.core.domain.model.delivery;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import libs.ddd.ValueObject;
import libs.errs.Err;
import libs.errs.Error;
import libs.errs.Result;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


@Embeddable
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Location extends ValueObject<Location> {

    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE= 10;

    @Column(name="location_x")
    private int x;
    @Column(name="location_y")
    private int y;

    public static Result<Location, Error> create(int x, int y) {
        var error = Err.combine(
                Err.againstOutOfRange(x, MIN_VALUE, MAX_VALUE, "x"),
                Err.againstOutOfRange(y, MIN_VALUE, MAX_VALUE, "y"));
        if (Objects.nonNull(error)) {
            return Result.failure(error);
        }
        return Result.success(new Location(x, y));
    }

    @Override
    protected Iterable<Object> equalityComponents() {
        return List.of(this.x, this.y);
    }

    public Result<Integer, Error> distanceTo(Location target) {
        if(Objects.isNull(target)) {
            return Result.failure(Error.of("target.is.not.null", "Target not be null"));
        }
        var distance = Math.abs(target.x - this.x) + Math.abs(target.y - this.y);
        return Result.success(distance);
    }
}
