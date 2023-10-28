import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOperator } from 'app/shared/model/operator.model';
import { getEntities as getOperators } from 'app/entities/operator/operator.reducer';
import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { ICinema } from 'app/shared/model/cinema.model';
import { getEntity, updateEntity, createEntity, reset } from './cinema.reducer';

export const CinemaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const operators = useAppSelector(state => state.operator.entities);
  const addresses = useAppSelector(state => state.address.entities);
  const cinemaEntity = useAppSelector(state => state.cinema.entity);
  const loading = useAppSelector(state => state.cinema.loading);
  const updating = useAppSelector(state => state.cinema.updating);
  const updateSuccess = useAppSelector(state => state.cinema.updateSuccess);

  const handleClose = () => {
    navigate('/cinema');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getOperators({}));
    dispatch(getAddresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cinemaEntity,
      ...values,
      operator: operators.find(it => it.id.toString() === values.operator.toString()),
      address: addresses.find(it => it.id.toString() === values.address.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...cinemaEntity,
          operator: cinemaEntity?.operator?.id,
          address: cinemaEntity?.address?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataTestApplicationApp.cinema.home.createOrEditLabel" data-cy="CinemaCreateUpdateHeading">
            <Translate contentKey="dataTestApplicationApp.cinema.home.createOrEditLabel">Create or edit a Cinema</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="cinema-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dataTestApplicationApp.cinema.name')}
                id="cinema-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                id="cinema-operator"
                name="operator"
                data-cy="operator"
                label={translate('dataTestApplicationApp.cinema.operator')}
                type="select"
              >
                <option value="" key="0" />
                {operators
                  ? operators.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="cinema-address"
                name="address"
                data-cy="address"
                label={translate('dataTestApplicationApp.cinema.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cinema" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CinemaUpdate;
