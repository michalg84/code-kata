package com.galka.lift;

import java.util.List;
import java.util.Optional;

public interface ILiftController {
    Optional<LiftEngineCommand> call(Call call);

    Optional<Direction> getCurrentDirection();

    LiftEngineCommand onFloor();

    int getCurrentFloor();

    Optional<LiftEngineCommand> onDoorsClosed();


    List<Call> getNextCalls();
}
