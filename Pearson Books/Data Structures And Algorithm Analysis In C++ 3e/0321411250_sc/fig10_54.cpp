static const int A = 48271;
static const int M = 2147483647;
class Random
{
  public:
    explicit Random( int initialValue = 1 );

    int randomInt( );
    double random0_1( );
    int randomInt( int low, int high );

  private:
    int state;
};
/**
 * Construct with initialValue for the state.
 */
Random::Random( int initialValue )
{
    if( initialValue < 0 )
        initialValue += M;
    state = initialValue;
    if( state == 0 )
        state = 1;
}
/**
 * Return a pseudorandom int, and change the
 * internal state. DOES NOT WORK CORRECTLY.
 * Correct implementation is in Figure 10.55.
 */
int Random::randomInt( )
{
    return state = ( A * state ) % M;
}
/**
 * Return a pseudorandom double in the open range 0..1
 * and change the internal state.
 */
double Random::random0_1( )
{
    return (double) randomInt( ) / M;
}
