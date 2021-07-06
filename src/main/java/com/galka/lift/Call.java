package com.galka.lift;

import java.util.Objects;

class Call {
    private final int floor;
    private Direction direction;

    public Call(int floor) {
        this.floor = floor;
    }

    public Call(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        String directionStr = "";
        if (direction != null) {
            directionStr = direction.symbol();// == Direction.UP ? "▲" : "▼";
        }
        return "(" + floor + directionStr + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Call)) return false;
        Call call = (Call) o;
        return floor == call.floor && direction == call.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, direction);
    }
}
