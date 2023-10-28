import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Period from './period';
import PeriodDetail from './period-detail';
import PeriodUpdate from './period-update';
import PeriodDeleteDialog from './period-delete-dialog';

const PeriodRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Period />} />
    <Route path="new" element={<PeriodUpdate />} />
    <Route path=":id">
      <Route index element={<PeriodDetail />} />
      <Route path="edit" element={<PeriodUpdate />} />
      <Route path="delete" element={<PeriodDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PeriodRoutes;
