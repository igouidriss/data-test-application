import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Address from './address';
import Operator from './operator';
import Cinema from './cinema';
import Room from './room';
import Period from './period';
import Session from './session';
import Movie from './movie';
import Casting from './casting';
import Actor from './actor';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="address/*" element={<Address />} />
        <Route path="operator/*" element={<Operator />} />
        <Route path="cinema/*" element={<Cinema />} />
        <Route path="room/*" element={<Room />} />
        <Route path="period/*" element={<Period />} />
        <Route path="session/*" element={<Session />} />
        <Route path="movie/*" element={<Movie />} />
        <Route path="casting/*" element={<Casting />} />
        <Route path="actor/*" element={<Actor />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
