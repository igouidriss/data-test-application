import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cinema.reducer';

export const CinemaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cinemaEntity = useAppSelector(state => state.cinema.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cinemaDetailsHeading">
          <Translate contentKey="dataTestApplicationApp.cinema.detail.title">Cinema</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cinemaEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="dataTestApplicationApp.cinema.name">Name</Translate>
            </span>
          </dt>
          <dd>{cinemaEntity.name}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.cinema.operator">Operator</Translate>
          </dt>
          <dd>{cinemaEntity.operator ? cinemaEntity.operator.id : ''}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.cinema.address">Address</Translate>
          </dt>
          <dd>{cinemaEntity.address ? cinemaEntity.address.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cinema" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cinema/${cinemaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CinemaDetail;
