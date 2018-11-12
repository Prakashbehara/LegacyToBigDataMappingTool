/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { DataModelMappingDetailComponent } from 'app/entities/data-model-mapping/data-model-mapping-detail.component';
import { DataModelMapping } from 'app/shared/model/data-model-mapping.model';

describe('Component Tests', () => {
    describe('DataModelMapping Management Detail Component', () => {
        let comp: DataModelMappingDetailComponent;
        let fixture: ComponentFixture<DataModelMappingDetailComponent>;
        const route = ({ data: of({ dataModelMapping: new DataModelMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataModelMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataModelMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataModelMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
