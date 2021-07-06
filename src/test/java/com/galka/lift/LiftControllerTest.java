package com.galka.lift;

import static com.galka.lift.Direction.*;
import static com.galka.lift.Direction.UP;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class LiftControllerTest {

    private final ILiftController lift = liftValueOf(0);
//    private final LiftController lift = new LiftController(0);

    private ILiftController liftValueOf(int floor) {
        return new LiftControllerLogger(new LiftController(floor), System.out::println);
    }

    @Test
    void shouldReturn0Floor_whenItStartsOn0() {
        assertThat(lift.getCurrentFloor()).isEqualTo(0);
    }

    @Test
    void shouldReturn2Floor_whenItStartsOn2() {
        assertThat(liftValueOf(2).getCurrentFloor()).isEqualTo(2);
    }

    @Test
    void displaysItGoesUp_whenCalledFromAbove() {
        lift.call(new Call(4));
        assertThat(lift.getCurrentDirection()).hasValue(UP);
    }

    @Test
    void displaysItGoesDown_whenCalledFromBellow() {
        lift.call(new Call(-2));
        assertThat(lift.getCurrentDirection()).hasValue(DOWN);
    }

    @Test
    void displaysEmptyDirection_whenThereIsNoCall() {
        assertThat(lift.getCurrentDirection()).isEmpty();
    }

    @Test
    void displaysNoDirection_initially() {
        lift.call(new Call(0));
        assertThat(this.lift.getCurrentDirection()).isEmpty();
    }

    @Test
    void requestToGoUp_whenCalledFromAbove() {
        final Optional<LiftEngineCommand> command = lift.call(new Call(1));
        assertThat(command).contains(LiftEngineCommand.GO_UP);
    }

    @Test
    void requestToGoDown_whenCalledFromBellow() {
        final Optional<LiftEngineCommand> command = lift.call(new Call(-3));
        assertThat(command).contains(LiftEngineCommand.GO_DOWN);
    }

    @Test
    void requestToOpenDoors_whenCalledFromSameFloor() {
        final Optional<LiftEngineCommand> command = lift.call(new Call(0));
        assertThat(command).contains(LiftEngineCommand.OPEN_DOORS);
    }

    @Test
    void displaysFirstFloor_afterOnFloorOnce_whenGoingUp() {
        lift.call(new Call(1));
        lift.onFloor();
        assertThat(lift.getCurrentFloor()).isEqualTo(1);
    }

    @Test
    void displaysMinusOneFloor_afterOnFloorOnce_whenGoingDown() {
        lift.call(new Call(-1));
        lift.onFloor();
        assertThat(lift.getCurrentFloor()).isEqualTo(-1);
    }

    @Test
    void reachesTheSecondFloor_whenCalledFormThere() {
        lift.call(new Call(2));
        assertThat(lift.onFloor()).isEqualTo(LiftEngineCommand.GO_UP);
        assertThat(lift.onFloor()).isEqualTo(LiftEngineCommand.OPEN_DOORS);
    }

    @Test
    void reachesTheMinusTwoFloor_whenCalledFormThere() {
        lift.call(new Call(-2));
        assertThat(lift.onFloor()).isEqualTo(LiftEngineCommand.GO_DOWN);
        assertThat(lift.onFloor()).isEqualTo(LiftEngineCommand.OPEN_DOORS);
    }

    @Test
    void displaysRequestedToGoToFirstFloor_whenRequestedToGoThere() {
        lift.call(new Call(1));
        assertThat(lift.getNextCalls()).containsExactly(new Call(1));
    }

    @Test
    void displaysCalledFromFirstFloor_whenCalledFromThere() {
        lift.call(new Call(1, UP));
        assertThat(lift.getNextCalls()).containsExactly(new Call(1, UP));
    }

    @Test
    void initiallyThereAreNoCallsInTheList() {
        assertThat(lift.getNextCalls()).isEmpty();
    }

    @Test
    void theFloorIsRemovedFromNextCalls_whenReachingThatFloorUp() {
        lift.call(new Call(1));
        lift.onFloor();
        assertThat(lift.getNextCalls()).isNotEmpty();
        lift.onDoorsClosed();
        assertThat(lift.getNextCalls()).isEmpty();
    }

    @Test
    void theFloorIsRemovedFromNextCalls_whenReachingThatFloorDown() {
        lift.call(new Call(-1));
        lift.onFloor();
        assertThat(lift.getNextCalls()).isNotEmpty();
        lift.onDoorsClosed();
        assertThat(lift.getNextCalls()).isEmpty();
    }






    /* @Test
    void invalidSequenceOfCode() { //impossible imputs assumed
        assertThat(lift.call(new Call(0)).isEmpty());
        lift.onFloor();         //this cannot happen
        assertThat(lift.getCurrentFloor()).isEqualTo(4);
    }*/
}


 /*   The Lift Kata
        Since lifts are everywhere and they contain software, how easy would it be to write a basic one?
        Let’s TDD a lift, starting with simple behaviors and working toward complex ones.
        Assume good input from calling code and concentrate on the main flow.

        Here are some suggested lift features:

        a lift responds to calls containing a source floor and direction
        a lift has an attribute floor, which describes it’s current location
        a lift delivers passengers to requested floors
        you may implement current floor monitor
        you may implement direction arrows
        you may implement doors (opening and closing)
        you may implement DING!
        there can be more than one lift
        Advanced Requirements
        a lift does not respond immediately. consider options to simulate time, possibly a tick method.
        lift calls are queued, and executed only as the lift passes a floor
        Objects Only
        Can you write a lift that does not need to be queried? Try writing a lift that only sends messages
         to other objects.*/