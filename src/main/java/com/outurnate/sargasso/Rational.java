package com.outurnate.sargasso;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.outurnate.sargasso.network.chat.RealContents;

import net.minecraft.network.chat.Component;

public record Rational(int numerator, int denominator) {
    @SuppressWarnings("null")
    public static final Codec<Rational> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("num").forGetter(Rational::numerator),
            Codec.INT.fieldOf("denum").forGetter(Rational::denominator)).apply(instance, Rational::new));

    public Rational add(Rational other) {
        int newNumerator = (this.numerator * other.denominator) + (other.numerator * this.denominator);
        int newDenominator = this.denominator * other.denominator;
        int gcd = Utils.gcd(Math.abs(newNumerator), Math.abs(newDenominator));
        if (newDenominator < 0) {
            newNumerator = -newNumerator;
            newDenominator = -newDenominator;
        }
        return new Rational(newNumerator / gcd, newDenominator / gcd);
    }

    public Rational clamp(int min, int max) {
        int value = this.numerator / this.denominator;
        if (value > max) {
            return new Rational(max, 1);
        }
        if (value < min) {
            return new Rational(min, 1);
        }
        return this;
    }

    public Component toComponent(String format) {
        return RealContents
            .localizedReal((float) this.numerator / (float) this.denominator, format);
    }
}
