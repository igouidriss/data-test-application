import address from 'app/entities/address/address.reducer';
import operator from 'app/entities/operator/operator.reducer';
import cinema from 'app/entities/cinema/cinema.reducer';
import room from 'app/entities/room/room.reducer';
import period from 'app/entities/period/period.reducer';
import session from 'app/entities/session/session.reducer';
import movie from 'app/entities/movie/movie.reducer';
import casting from 'app/entities/casting/casting.reducer';
import actor from 'app/entities/actor/actor.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  address,
  operator,
  cinema,
  room,
  period,
  session,
  movie,
  casting,
  actor,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
