import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './period.reducer';

export const PeriodDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const periodEntity = useAppSelector(state => state.period.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="periodDetailsHeading">
          <Translate contentKey="dataTestApplicationApp.period.detail.title">Period</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{periodEntity.id}</dd>
          <dt>
            <span id="start">
              <Translate contentKey="dataTestApplicationApp.period.start">Start</Translate>
            </span>
          </dt>
          <dd>{periodEntity.start ? <TextFormat value={periodEntity.start} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="end">
              <Translate contentKey="dataTestApplicationApp.period.end">End</Translate>
            </span>
          </dt>
          <dd>{periodEntity.end ? <TextFormat value={periodEntity.end} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.period.room">Room</Translate>
          </dt>
          <dd>{periodEntity.room ? periodEntity.room.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/period" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/period/${periodEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PeriodDetail;
