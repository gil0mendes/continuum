package com.continuum.datastructures;

public class BlockmaniaSmartArray {

    private final byte _array[];
    private final int _lX, _lY, _lZ;
    private final int _size, _halfSize;

    public BlockmaniaSmartArray(int x, int y, int z) {
        _lX = x;
        _lY = y;
        _lZ = z;

        _size = _lX * _lY * _lZ;
        _array = new byte[_halfSize = _size / 2];
    }

    public byte get(int x, int y, int z) {

        int pos = (x * _lX * _lY) + (y * _lX) + z;

        if (x >= _lX || y >= _lY || z >= _lZ || x < 0 || y < 0 || z < 0)
            return -1;

        if (pos < _halfSize) {
            int bArray = _array[pos] & 0xFF;
            return (byte) ((bArray & 0x0F) & 0xFF);
        }

        int bArray = _array[pos % _halfSize] & 0xFF;
        return (byte) (bArray >> 4);
    }

    public void set(int x, int y, int z, byte b) {
        int pos = (x * _lX * _lY) + (y * _lX) + z;

        if (x >= _lX || y >= _lY || z >= _lZ || x < 0 || y < 0 || z < 0)
            return;

        if (pos < _halfSize) {
            int bArray = _array[pos] & 0xFF;
            int bInput = b & 0xFF;
            _array[pos] = (byte) ((bInput & 0x0F) | (bArray & 0xF0));
            return;
        }

        int bArray = _array[pos % _halfSize] & 0xFF;
        int bInput = b & 0xFF;
        _array[pos % _halfSize] = (byte) ((bArray & 0x0F) | (bInput << 4) & 0xFF);
    }

    public byte getRawByte(int i) {
        return _array[i];
    }

    public void setRawByte(int i, byte b) {
        _array[i] = b;
    }

    public int getSize() {
        return _size;
    }

    public int getPackedSize() {
        return _halfSize;
    }
}