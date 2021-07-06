package com.galka.lift;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LiftController implements ILiftController {
    private Call currentCall;
    private int currentFloor;

    public LiftController(int initialFloor) {
        this.currentFloor = initialFloor;
    }

    @Override
    public Optional<LiftEngineCommand> call(Call call) {
        currentCall = call;
        if (currentCall.getFloor() > currentFloor) {
            return of(LiftEngineCommand.GO_UP);
        } else if (currentCall.getFloor() < currentFloor) {
            return of(LiftEngineCommand.GO_DOWN);
        }
        return of(LiftEngineCommand.OPEN_DOORS);
    }


    @Override
    public Optional<Direction> getCurrentDirection() {
        if (currentCall == null) {
            return empty();
        }
        if (currentCall.getFloor() > currentFloor) {
            return of(Direction.UP);
        } else if (currentCall.getFloor() < currentFloor) {
            return of(Direction.DOWN);
        }
        return empty();
    }


    @Override
    public LiftEngineCommand onFloor() {
        if (currentCall.getFloor() > currentFloor) {
            currentFloor++;
            if (currentCall.getFloor() == currentFloor) {
                return LiftEngineCommand.OPEN_DOORS;
            } else {
                return LiftEngineCommand.GO_UP;
            }
        } else if (currentCall.getFloor() < currentFloor) {
            currentFloor--;
            if (currentCall.getFloor() == currentFloor) {
                return LiftEngineCommand.OPEN_DOORS;
            } else {
                return LiftEngineCommand.GO_DOWN;
            }
        } else {
            return LiftEngineCommand.OPEN_DOORS;
        }
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public Optional<LiftEngineCommand> onDoorsClosed() {
        if (currentCall.getFloor() == currentFloor) {
            currentCall = null;
        }
        return empty();
    }

    @Override
    public List<Call> getNextCalls() {
        if (currentCall == null) {
            return Collections.emptyList();
        }
        return List.of(currentCall);
    }

}

