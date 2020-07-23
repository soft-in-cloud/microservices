import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Error from './error';
import ErrorDetail from './error-detail';
import ErrorUpdate from './error-update';
import ErrorDeleteDialog from './error-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ErrorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ErrorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ErrorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Error} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ErrorDeleteDialog} />
  </>
);

export default Routes;
