package com.galka.lift;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LiftControllerLogger implements ILiftController {

    private LiftController delegate;
    private Consumer<String> logger;

    public LiftControllerLogger(LiftController delegate, Consumer<String> logger) {
        this.delegate = delegate;
        this.logger = logger;
    }

    @Override
    public Optional<LiftEngineCommand> call(Call call) {
        final Optional<LiftEngineCommand> result = delegate.call(call);
        dumpState("CALL" + call, result.map(Objects::toString).orElse("NOOP"));
        return result;
    }

    @Override
    public LiftEngineCommand onFloor() {
        final LiftEngineCommand command = delegate.onFloor();
        dumpState("FLOOR", command);
        return command;
    }

    private void dumpState(String event, Object result) {
        String directionStr = delegate.getCurrentDirection().map(Direction::symbol).orElse("-");
        String leftCalls = delegate.getNextCalls().stream().sorted(comparator).map(Call::toString).collect(Collectors.joining(" "));
        String floorStr = String.valueOf(delegate.getCurrentFloor());
        logger.accept(String.format("%12s > %10s on %2s %s: %s",event, result, floorStr, directionStr, leftCalls));
    }

    @Override
    public List<Call> getNextCalls() {
        return delegate.getNextCalls();
    }

    @Override
    public Optional<Direction> getCurrentDirection() {
        return delegate.getCurrentDirection();
    }

    @Override
    public int getCurrentFloor() {
        return delegate.getCurrentFloor();
    }

    @Override
    public Optional<LiftEngineCommand> onDoorsClosed() {
        return delegate.onDoorsClosed();
    }

    private final Comparator<Call> comparator = Comparator.comparing(Call::getFloor)
            .thenComparing(call -> "" + call.getDirection());

}
